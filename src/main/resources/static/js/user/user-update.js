$(function () {
  $("#search").click(function () {
    $.ajax({
      url: "/users/" + $('#search-input').val(),
      type: "get",
      dataType: "json",
      success: function (data) {
        $('#nickname').attr('placeholder', data.data.nickname);
        $('#email').attr('placeholder', data.data.email);
        $('#phone').attr('placeholder', data.data.phone);
        $('#address').attr('placeholder', data.data.address);
        $('#size').attr('placeholder', data.data.size);
      },
      error: function (err) {
        alert("수정을 할 수 없습니다.");
      }
    });
  })

  $('form[name=data] button').click(function () {
    const form = {
      "option": $(this).prev().prev().attr('id'),
      "value": $(this).prev().prev().val()
    }
    $.ajax({
      url: "/users/" + $('#search-input').val(),
      type: "patch",
      data: JSON.stringify(form),
      dataType: 'json',
      contentType: 'application/json',
      success: function (data) {
        console.log(data);
      },
      error: function (err) {
        alert(err);
      }
    });
  })
})
