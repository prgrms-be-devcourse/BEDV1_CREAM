$(function () {
  $("#send").click(function () {
    $.ajax({
      url: "/users/" + $('#search-input').val(),
      type: "get",
      dataType: "json",
      success: function (data) {
        $('#user-nickname').html(data.data.nickname);
        $('#user-email').html(data.data.email)
        $('#user-phone').html(data.data.phone)
        $('#user-address').html(data.data.address)
        $('#user-size').html(data.data.size)
      },
      error: function (err) {
        alert(err);
      }
    });
  })
})
