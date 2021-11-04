package org.prgrms.cream.domain.product.service;

import static org.prgrms.cream.domain.product.repository.ProductSpecification.*;
import static org.springframework.data.jpa.domain.Specification.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.dto.BidDetail;
import org.prgrms.cream.domain.deal.dto.BuyingBidPriceResponse;
import org.prgrms.cream.domain.deal.dto.DealPriceResponse;
import org.prgrms.cream.domain.deal.dto.SellingBidPriceResponse;
import org.prgrms.cream.domain.deal.repository.BuyingRepository;
import org.prgrms.cream.domain.deal.repository.DealRepository;
import org.prgrms.cream.domain.deal.repository.SellingRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.prgrms.cream.domain.product.dto.DetailResponse;
import org.prgrms.cream.domain.product.dto.OptionResponse;
import org.prgrms.cream.domain.product.dto.ProductRequest;
import org.prgrms.cream.domain.product.dto.ProductResponse;
import org.prgrms.cream.domain.product.dto.ProductsResponse;
import org.prgrms.cream.domain.product.exception.NotFoundProductException;
import org.prgrms.cream.domain.product.exception.NotFoundProductOptionException;
import org.prgrms.cream.domain.product.repository.ProductOptionRepository;
import org.prgrms.cream.domain.product.repository.ProductRepository;
import org.prgrms.cream.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

	private static final int NO_BID = 0;
	private static final int NO_DEAL = 0;
	private static final int ZERO = 0;

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;
	private final DealRepository dealRepository;
	private final BuyingRepository buyingRepository;
	private final SellingRepository sellingRepository;

	public ProductService(
		ProductRepository productRepository,
		ProductOptionRepository productOptionRepository,
		DealRepository dealRepository,
		BuyingRepository buyingRepository,
		SellingRepository sellingRepository
	) {
		this.productRepository = productRepository;
		this.productOptionRepository = productOptionRepository;
		this.dealRepository = dealRepository;
		this.buyingRepository = buyingRepository;
		this.sellingRepository = sellingRepository;
	}

	@Transactional(readOnly = true)
	public List<ProductsResponse> getProducts(Map<String, String> filter) {
		return productRepository
			.findAll(where(filterProduct(filter)))
			.stream()
			.map(this::toProductResponse)
			.toList();
	}

	@Transactional(readOnly = true)
	public ProductResponse getProduct(Long id) {
		Product product = findActiveProduct(id);

		return new ProductResponse(
			product,
			productOptionRepository
				.findByProduct(product)
				.stream()
				.map(productOption -> new OptionResponse(
					productOption.getSize(),
					productOption.getLowestPrice(),
					productOption.getHighestPrice()
				))
				.toList()
		);
	}

	@Transactional(readOnly = true)
	public DetailResponse getProductDetail(Long id) {
		Product product = findActiveProduct(id);

		Optional<Deal> optDeal = dealRepository.findFirstByProductAndIsFinishedTrueOrderByCreatedDateDesc(
			product);
		int recentDealPrice = optDeal.isEmpty() ? NO_DEAL : optDeal
			.get()
			.getPrice();

		List<Deal> dealPrices = dealRepository.findAllByProductAndIsFinishedTrueOrderByCreatedDateDesc(
			product);
		List<BidDetail> buyingBids = buyingRepository.findAllByProductGroupBy(product.getId());
		List<BidDetail> sellingBids = sellingRepository.findAllByProductGroupBy(product.getId());

		return toDetailResponse(recentDealPrice, dealPrices, buyingBids, sellingBids);
	}

	@Transactional(readOnly = true)
	public DetailResponse getProductDetailByOption(Long id, String size) {
		Product product = findActiveProduct(id);

		Optional<Deal> optDeal = dealRepository.findFirstByProductAndSizeAndIsFinishedTrueOrderByCreatedDateDesc(
			product, size);
		int recentDealPrice = optDeal.isEmpty() ? NO_DEAL : optDeal
			.get()
			.getPrice();

		List<Deal> dealPrices = dealRepository.findAllByProductAndSizeAndIsFinishedTrueOrderByCreatedDateDesc(
			product, size);
		List<BidDetail> buyingBids = buyingRepository.findAllByProductAndSizeGroupBy(
			product.getId(), size);
		List<BidDetail> sellingBids = sellingRepository.findAllByProductAndSizeGroupBy(
			product.getId(), size);

		return toDetailResponse(recentDealPrice, dealPrices, buyingBids, sellingBids);
	}

	@Transactional
	public Long registerProduct(ProductRequest productRequest) {
		Product product = productRequest.toEntity();
		for (String size : productRequest.getSizes()) {
			product.addOption(size);
		}

		return productRepository
			.save(product)
			.getId();
	}

	@Transactional
	public Long modifyProduct(Long id, ProductRequest productRequest) {
		Product product = findActiveProduct(id);
		product.changeProductInfo(productRequest);
		for (String size : productRequest.getSizes()) {
			modifyOption(product, size);
		}
		return product.getId();
	}

	@Transactional
	public void removeProduct(Long id) {
		Product product = findActiveProduct(id);
		product.deleteProduct();
	}

	@Transactional(readOnly = true)
	public ProductOption findProductOptionByProductIdAndSize(Long id, String size) {
		return productOptionRepository
			.findByProductAndSize(findActiveProduct(id), size)
			.orElseThrow(() -> new NotFoundProductOptionException(ErrorCode.NOT_FOUND_RESOURCE));
	}

	@Transactional(readOnly = true)
	public Product findActiveProduct(Long id) {
		return productRepository
			.findByIdAndIsDeletedFalse(id)
			.orElseThrow(() -> new NotFoundProductException(ErrorCode.NOT_FOUND_RESOURCE));
	}

	private ProductsResponse toProductResponse(Product product) {
		Optional<ProductOption> optLowestPrice = productOptionRepository
			.findFirstByProductAndLowestPriceNotOrderByLowestPrice(product, ZERO);

		Optional<ProductOption> optHighestPrice = productOptionRepository
			.findFirstByProductOrderByHighestPriceDesc(product);

		int lowestPrice = optLowestPrice.isEmpty() ? NO_BID : optLowestPrice
			.get()
			.getLowestPrice();

		int highestPrice = optHighestPrice.isEmpty() ? NO_BID : optHighestPrice
			.get()
			.getHighestPrice();

		return new ProductsResponse(product, lowestPrice, highestPrice);
	}

	private DetailResponse toDetailResponse(
		int recentDealPrice,
		List<Deal> dealPrices,
		List<BidDetail> buyingBids,
		List<BidDetail> sellingBids
	) {

		List<DealPriceResponse> dealPriceRes = dealPrices
			.stream()
			.map(deal -> new DealPriceResponse(
				deal.getSize(),
				deal.getPrice(),
				deal
					.getCreatedDate()
					.format(DateTimeFormatter.ofPattern("yy/MM/dd"))
			))
			.toList();

		List<BuyingBidPriceResponse> buyingBidRes = buyingBids
			.stream()
			.map(buyingBid -> new BuyingBidPriceResponse(
				buyingBid.getSize(),
				buyingBid.getPrice(),
				buyingBid.getQuantity()
			))
			.toList();

		List<SellingBidPriceResponse> sellingBidRes = sellingBids
			.stream()
			.map(sellingBid -> new SellingBidPriceResponse(
				sellingBid.getSize(),
				sellingBid.getPrice(),
				sellingBid.getQuantity()
			))
			.toList();

		return new DetailResponse(recentDealPrice, dealPriceRes, buyingBidRes, sellingBidRes);
	}

	private void modifyOption(Product product, String size) {
		boolean isExist = productOptionRepository.existsByProductAndSize(product, size);
		if (!isExist) {
			product.addOption(size);
		}
	}
}
