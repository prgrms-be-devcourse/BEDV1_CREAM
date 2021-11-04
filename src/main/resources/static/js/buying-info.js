$('#btn-bidding').on('click', getBidding);
$('#btn-pending').on('click', getPending);
$('#btn-finished').on('click', getFinished);

function getBidding() {

  let userId = $('#userId').val();
  let status = $('#status option:selected').val();
  let url = '/users/' + userId + '/buying/bidding';
  url = status === '' ? url : url + '?status=' + status;

  $.ajax({
    type: 'GET',
    url: url
  }).done(function (response) {

    let body =
        '<thead>'
        + '<tr>'
        + '<th>이미지</th>'
        + '<th>상품명</th>'
        + '<th>사이즈</th>'
        + '<th>구매 희망가</th>'
        + '<th>상태</th>'
        + '<th>만료일</th>'
        + '</tr>'
        + '</thead>';
    $.each(response.data.bidHistory, (idx, bid) => {
      body += '<tr>';
      body += '<td><img src=\"' + bid.image
          + '\" style=\"width:100px; height:100px; \"\/></td>';
      body += '<td>' + bid.productName + '</td>';
      body += '<td>' + bid.size + '</td>';
      body += '<td>' + bid.suggestPrice + '</td>';
      body += '<td>' + bid.status + '</td>';
      body += '<td>' + bid.expiredDate + '</td>';
      body += '</tr>';
    });
    $("#dynamicTable").empty();
    $("#dynamicTable").append(body);
    $("select#status option").remove();
    $("select#status").append("<option selected value=\"\">전체</option>");
    $("select#status").append("<option value=\"입찰 중\">입찰 중</option>");
    $("select#status").append("<option value=\"기한만료\">기한만료</option>");
  }).fail(function (error) {
    alert('에러코드 : ' + error.responseJSON.status + '\n'
        + error.responseJSON.message);
  });
}

function getPending() {
  let userId = $('#userId').val();
  let status = $('#status option:selected').val();
  let url = '/users/' + userId + '/buying/pending';
  url = status === '' ? url : url + '?status=' + status;

  $.ajax({
    type: 'GET',
    url: url
  }).done(function (response) {
    let body =
        '<thead>'
        + '<tr>'
        + '<th>이미지</th>'
        + '<th>상품명</th>'
        + '<th>사이즈</th>'
        + '<th>상태</th>'
        + '</tr>'
        + '</thead>';
    $.each(response.data.dealHistory, (idx, deal) => {
      body += '<tr>';
      body += '<td><img src=\"' + deal.image
          + '\" style=\"width:100px; height:100px; \"\/></td>';
      body += '<td>' + deal.productName + '</td>';
      body += '<td>' + deal.size + '</td>';
      body += '<td>' + deal.status + '</td>';
      body += '</tr>';
    });
    $("#dynamicTable").empty();
    $("#dynamicTable").append(body);

    $("select#status option").remove();
    $("select#status").append("<option selected value=\"\">전체</option>");
    $("select#status").append("<option value=\"검수 중\">검수 중</option>");
    $("select#status").append("<option value=\"검수합격\">검수합격</option>");
    $("select#status").append("<option value=\"배송 중\">배송 중</option>");
  }).fail(function (error) {
    alert('에러코드 : ' + error.responseJSON.status + '\n'
        + error.responseJSON.message);
  });
}

function getFinished() {
  let userId = $('#userId').val();
  let status = $('#status option:selected').val();
  let url = '/users/' + userId + '/buying/finished';
  url = status === '' ? url : url + '?status=' + status;

  $.ajax({
    type: 'GET',
    url: url
  }).done(function (response) {
    let body =
        '<thead>'
        + '<tr>'
        + '<th>이미지</th>'
        + '<th>상품명</th>'
        + '<th>사이즈</th>'
        + '<th>구매일</th>'
        + '<th>상태</th>'
        + '</tr>'
        + '</thead>';
    $.each(response.data.dealHistory, (idx, deal) => {
      body += '<tr>';
      body += '<td><img src=\"' + deal.image
          + '\" style=\"width:100px; height:100px; \"\/></td>';
      body += '<td>' + deal.productName + '</td>';
      body += '<td>' + deal.size + '</td>';
      body += '<td>' + deal.buyDate + '</td>';
      body += '<td>' + deal.status + '</td>';
      body += '</tr>';
    });
    $("#dynamicTable").empty();
    $("#dynamicTable").append(body);

    $("select#status option").remove();
    $("select#status").append("<option selected value=\"\">전체</option>");
    $("select#status").append("<option value=\"배송완료\">배송완료</option>");
    $("select#status").append("<option value=\"취소완료\">취소완료</option>");
  }).fail(function (error) {
    alert('에러코드 : ' + error.responseJSON.status + '\n'
        + error.responseJSON.message);
  });
}
