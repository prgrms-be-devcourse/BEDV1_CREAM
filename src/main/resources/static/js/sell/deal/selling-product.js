$(function () {
  $("#search").click(function () {
    $.ajax({
      url: "/products/" + $('#search-input').val(),
      type: "get",
      dataType: "json",
      success: function (data) {
        $('#product-brand').html(data.data.brand);
        $('#product-koreanName').html(data.data.koreanName)
        $('#product-color').html(data.data.color)
        $('#product-image').html(
            '<img src = ' + data.data.image + ' widht = 100 height = 100>')
        $('#product-releasePrice').html(data.data.releasePirce)
      },
      error: function (err) {
        alert("ajax 에러 발생");
      }
    });
  })

  $("#sell-bid").click(function () {
    let productId = $('#search-input').val();
    let size = $('#size').val();
    console.log('productId : ' + productId);
    console.log('size : ' + size);
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
        alert('성공');
      },
      error: function (err) {
        alert("ajax 에러 발생");
      }
    });
  })

  $("#straight-sell").click(function () {
    let productId = $('#search-input').val();
    let size = $('#size').val();
    console.log('productId : ' + productId);
    console.log('size : ' + size);
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
        alert('성공');
      },
      error: function (err) {
        alert("ajax 에러 발생");
      }
    });
  })
})
