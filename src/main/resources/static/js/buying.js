$('#btn-product').on('click', getProduct);

function getProduct() {
  let productId = $("#productId").val();
  let size = $("#size").val();

  $.ajax({
    type: 'GET',
    url: '/products/' + productId + '?size=' + size
  }).done(function (response) {
    console.log(response)
    $('#product-image').attr('src', response.data.image);
    $('#product-englishName').text(response.data.englishName);
    $('#product-koreanName').text(response.data.koreanName);

    $.each(response.data.options, (idx, product) => {
      if (product.size === size) {
        $('#product-size').text(product.size);
        $('#product-straightBuyPrice').text(product.straightBuyPrice);
        $('#product-straightSellPrice').text(product.straightSellPrice);
      }
    });
  }).fail(function (error) {
    alert('에러코드 : ' + error.responseJSON.status + '\n'
        + error.responseJSON.message);
  })
}

$('#btn-buyingbid').on('click', buying);

function buying() {
  let productId = $('#productId').val();
  let userId = $('#userId').val();
  let price = $('#price').val();
  let deadline = $('#deadline').val();
  let size = $('#size').val();

  let sendData = {
    "price": price,
    "deadline": deadline,
    "userId": userId
  }

  $.ajax({
    type: 'PUT',
    url: '/buying/' + productId + '?size=' + size,
    data: JSON.stringify(sendData),
    dataType: 'json',
    contentType: 'application/json; charset=utf-8',
  }).done(function (response) {
    $("#result-price").text('구매 희망가 : ' + response.data.price);
    $("#result-deadline").text('입찰 마감 기한 : ' + response.data.deadline + '일');
    $("#result-expiredDate").text(response.data.expiredDate + '까지');
  }).fail(function (error) {
    alert('에러코드 : ' + error.responseJSON.status + '\n'
        + error.responseJSON.message);
  })
}

$('#btn-straightBuy').on('click', straightBuy);

function straightBuy() {
  let productId = $("#productId").val();
  let size = $("#size").val();
  let userId = $("#userId").val();

  let sendData = {
    "userId": userId
  }

  $.ajax({
    type: 'POST',
    url: '/buying/' + productId + '?size=' + size,
    data: JSON.stringify(sendData),
    dataType: 'json',
    contentType: 'application/json; charset=utf-8',
  }).done(function (response) {
    $('#straight-dealId').text('거래 체결 ID : ' + response.data.dealId);
    $('#straight-productName').text('거래 상품 : ' + response.data.productName);
    $('#straight-size').text('사이즈 : ' + response.data.size);
    $('#straight-price').text('체결 거래가 : ' + response.data.price);
    $('#straight-createdDate').text('체결일자 : ' + response.data.createdDate);
  }).fail(function (error) {
    alert('에러코드 : ' + error.responseJSON.status + '\n'
        + error.responseJSON.message);
  })
}
