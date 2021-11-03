$(function () {
  let userId = $('#userId').val();
  let status = $('#status').val();
  $("#send").click(function () {
    $.ajax({
      url: "/selling/bidding/" + userId + "/" + status,
      type: "get",
      data: {
        "status": status
      },
      dataType: "json",
      success: function (data) {
        $('#user-nickname').html(data.data.nickname);
        $('#user-email').html(data.data.email)
        $('#user-phone').html(data.data.phone)
        $('#user-address').html(data.data.address)
        $('#user-size').html(data.data.size)
      },
      error: function (err) {
        alert("ajax 에러 발생");
      }
    });
  })
})
