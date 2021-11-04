$(function () {
  $("#search").click(function () {

    let productId = $("#search-input").val();
    let size = $("#size").val();

    $.ajax({
      url: "/products/" + productId + '?size=' + size,
      type: "get",
      dataType: "json",
      success: function (data) {
        console.log(data);
        $('#product-brand').html(data.data.brand);
        $('#product-koreanName').html(data.data.koreanName)
        $('#product-color').html(data.data.color)
        $('#product-image').html(
            '<img src = ' + data.data.image + ' widht = 100 height = 100>')

    
            $.each(data.data.options, (idx, product) => {
              if(size == product.size){
                console.log("check " + product.straightSellPrice);
                $('#product-straightSellPrice').html(product.straightBuyPrice + "원")
              }
            })
        
      },
      error: function (err) {
        alert("존재하지 않는 상품입니다.");
      }
    });
  })

  $("#sell-bid").click(function () {
    let productId = $('#search-input').val();
    let size = $('#sell-size').val();
    let url = '/selling/' + productId + '?size=' + size;

    const form1 = {
      "price": $('#sellPrice').val(),
      "deadline": $('#deadLine').val(),
      "userId": $('#userId').val()
    }

    $.ajax({
      url: url,
      type: 'put',
      data: JSON.stringify(form1),
      dataType: "json",
      contentType: 'application/json',
      success: function (data) {
        console.log(data);
        alert('판매 입찰 등록!');
      },
      error: function (err) {
        alert("ajax 에러 발생");
      }
    });
  })

  $("#straight-sell").click(function () {
    let productId = $('#search-input').val();
    let size = $('#sell-size').val();
    let url = '/selling/' + productId + '?size=' + size;

    const form1 = {
      "userId": $('#userId').val()
    }

    $.ajax({
      url: url,
      type: "post",
      data: JSON.stringify(form1),
      dataType: "json",
      contentType: 'application/json',
      success: function (data) {
        console.log(data);
        alert('즉시판매!');
      },
      error: function (err) {
        alert("ajax 에러 발생");
      }
    });
  })
})
