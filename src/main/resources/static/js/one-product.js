let temp = location.href.split("?");
let productId = temp[1];

let productInfo = null;
let productDetail = null;
let options = {};
let size = "모든 사이즈";
let price = 0;

$(function () {
  $.ajax({
    type: "GET",
    url: "/products/" + productId,
    success: (infoRes) => {
      productInfo = infoRes.data;
      options = infoRes.data.options;
      $.ajax({
        type: "GET",
        url: "/products/" + productId + "/details",
        success: (detailRes) => {
          productDetail = detailRes.data;
          price = productDetail.recentDealPrice;
          console.log(productInfo);
          console.log(productDetail);

          $("#product-image").append('<img src="' + productInfo.image
              + '"style="max-width: 100%; height: auto;"/>');

          $("#product-info1").append('<colgroup><col style="width:50%">'
              + '<col style="width:50%"></colgroup>'
              + '<tr><td colspan="2" style="text-align:left;">'
              + '<h3>' + productInfo.brand + '</h3>'
              + '<h4>' + productInfo.englishName + '</h4>'
              + '<h5>' + productInfo.koreanName + '</h5></td></tr>'
              + '<tr><td>사이즈</td><td>' + size + '</td></tr>'
              + '<tr><td>최근 거래가</td><td>' + price + '</td></tr>'
              + '<tr>'
              + '<td><button class="btn btn-danger btn-block">구매</button></td>'
              + '<td><button class="btn btn-success btn-block">판매</button></td>'
              + '</tr>');

          $("#product-info2").append(
              '<colgroup><col style="width:25%"><col style="width:25%">'
              + '<col style="width:25%"> <col style="width:25%"></colgroup>'
              + '<tr><td colspan="4" style="text-align:left;"><br/>'
              + '<h3>상품 정보</h3></td></tr>'
              + '<tr class="column-name"><td>모델번호</td><td>출시일</td>'
              + '<td>컬러</td><td>발매가</td></tr>'
              + '<tr class="column-name">'
              + '<td>' + productInfo.modelNumber + '</td>'
              + '<td>' + productInfo.releaseDate + '</td>'
              + '<td>' + productInfo.color + '</td>'
              + '<td>' + productInfo.releasePrice + '</td></tr>');

          getDetail();
          $.each(productDetail.dealPriceResponses, (idx, deal) => {
            let dealRow = '<tr>' +
                '<td>' + deal.size + '</td>' +
                '<td>' + deal.dealPrice + '</td>' +
                '<td>' + deal.dealDate + '</td></tr>';
            $('#deal-info tbody').append(dealRow);
          });
        }
      });
    }
  });
});

function getDetail() {
  $('.tab-menu').click(function () {
    const activeTab = $(this).attr('data-tab');
    $(".tab-menu").css('background-color', 'white');
    $(this).css('background-color', '#F6F8F9').css('border-radius', '35px');

    $('#product-detail').empty();
    if (activeTab === "deal") {
      let pre = '<thead><tr><td>사이즈</td>'
          + '<td>거래가</td><td>거래일</td></tr></thead>';
      $('#product-detail').append(pre);

      $.each(productDetail.dealPriceResponses, (idx, deal) => {
        let dealRow =
            '<tbody><tr>'
            + '<td>' + deal.size + '</td>'
            + '<td>' + deal.dealPrice + '</td>'
            + '<td>' + deal.dealDate + '</td></tr></tbody>';
        $('#product-detail').append(dealRow);
      });
    } else if (activeTab === "selling") {
      let pre = '<thead><tr><td>사이즈</td>'
          + '<td>판매 희망가</td><td>수량</td></tr></thead>';
      $('#product-detail').append(pre);

      $.each(productDetail.sellingBidPriceResponses, (idx, sell) => {
        let sellRow = '<tbody><tr>'
            + '<td>' + sell.size + '</td>'
            + '<td>' + sell.sellingPrice + '</td>'
            + '<td>' + sell.quantity + '</td></tr></tbody>';
        $('#product-detail').append(sellRow);
      });
    } else if (activeTab === "buying") {
      let pre = '<thead><tr><td>사이즈</td>'
          + '<td>구매 희망가</td><td>수량</td></tr></thead>';
      $('#product-detail').append(pre);

      $.each(productDetail.buyingBidPriceResponses, (idx, buy) => {
        let sellRow = '<tbody><tr>'
            + '<td>' + buy.size + '</td>'
            + '<td>' + buy.buyingPrice + '</td>'
            + '<td>' + buy.quantity + '</td></tr></tbody>';
        $('#product-detail').append(sellRow);
      });
    }
  });
  $('#default').click();
}

