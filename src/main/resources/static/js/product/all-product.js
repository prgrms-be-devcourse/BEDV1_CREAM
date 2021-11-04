$(function () {
  $.ajax({
    type: "GET",
    url: "/products/search",
    success: (response) => {
      console.log(response);
      $.each(response.data, (idx, product) => {
        let productRow = '<tr style="cursor:pointer;" '
            + 'onClick="location.href=\'/product/one-product.html?' + product.id
            + '\'">'
            + '<td class=\"td_image\"><img src="' + product.image
            + '"style="width:100px; height:100px;"/></td>' +
            '<td class=\"td_brand\">' + product.brand + '</td>' +
            '<td class=\"td_name\">' + product.englishName + '</td>' +
            '<td class=\"td_name\">' + product.koreanName + '</td>' +
            '<td class=\"td_buy_price\">' + product.straightBuyPrice + '</td>' +
            '<td class=\"td_sell_price\">' + product.straightSellPrice + '</td>'
            +
            '</tr>';
        $('#productList tbody').append(productRow);
      });
    }
  });
});
