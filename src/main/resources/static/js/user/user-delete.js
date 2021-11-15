$(function () {
  $("#delete-send").click(function () {
    $.ajax({
      url: "/users/" + $('#search-input').val(),
      type: "delete",
      dataType: "json",
      success: function (data) {
        console.log(data);
        alert("회원 탈퇴 성공");
        location.href = "/";
      },
      error: function (err) {
        alert("회원탈퇴중 오류가 발생했습니다.");
      }
    });
  })
})
