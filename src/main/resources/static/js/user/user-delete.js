$(function () {
  $("#send").click(function () {
    $.ajax({
      url: "/users/" + $('#search-input').val(),
      type: "delete",
      dataType: "json",
      success: function (data) {
        console.log(data);
      },
      error: function (err) {
        alert(err);
      }
    });
  })
})
