$(function () {

  $("#send").click(function () {
    let userId = $('#search-input').val();
    let status = $('#status option:selected').val();
    status = encodeURI(status);

    if ((userId != localStorage.getItem('userId')) && (userId != '')) {
      localStorage.setItem('userId', userId);
    }

    userId = localStorage.getItem('userId');
    let url = '/users/' + userId + '/selling/bidding';
    url = status === '' ? url : url + '?status=' + status;

    $.ajax({
      url: url,
      type: "get",
      dataType: "json",
      success: function (data) {
        let html = '';
        $("#table tr:gt(0)").remove();
        $.each(data.data, function (index, item) {
          for (key in item) {
            html += '<tr>';
            html += '<td padding = 150px >' + '<img src=' + item[key].image
                + ' width = "100" height = "100" >'
                + '</td>';
            html += '<td>' + item[key].name + '</td>';
            html += '<td>' + item[key].size + '</td>';
            html += '<td>' + item[key].price + '</td>';
            html += '<td>' + item[key].expiredDate + '</td>';
            html += '</tr>';
          }
        })
        $("#dynamicTbody").empty();
        $("#dynamicTbody").append(html);
      },
      error: function (err) {
        alert("내역을 조회할 수 없습니다.");
      }
    });
  })
})
