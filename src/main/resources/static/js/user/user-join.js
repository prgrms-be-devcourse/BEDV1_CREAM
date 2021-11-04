$(function () {
  $("#join-send").click(function () {
    const form1 =
        {
          "nickname": $('#nickname').val(),
          "email": $('#email').val(),
          "phone": $('#phone').val(),
          "address": $('#address').val(),
          "size": $('#size').val(),
        }
    $.ajax({
      url: "/users",
      type: "post",
      data: JSON.stringify(form1),
      dataType: 'json',
      contentType: 'application/json',
      success: function (data) {
        console.log(data);
      },
      error: function (err) {
        alert("회원가입을 할 수 없습니다.");
      }
    });
  })
})
