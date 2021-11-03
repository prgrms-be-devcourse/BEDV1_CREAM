$(function () {
  $("#search").click(function () {
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
