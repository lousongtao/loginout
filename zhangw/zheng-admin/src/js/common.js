$(function() {
    // Waves初始化
    Waves.displayEffect();
    // 数据表格动态高度
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView', {
            height: getHeight()
        });
    });
    // 设置input特效
    $(document).on('focus', 'input,textarea', function() {
        $(this).parent().find('label').addClass('active');
    }).on('blur',  'input,textarea', function() {
        // if ($(this).val() == '') {
        // 	$(this).parent().find('label').removeClass('active');
        // }
    });
    // select2初始化
    $('select').select2();
});
// 动态高度
function getHeight() {
    return $(window).height() - 20;
}
// 数据表格展开内容
function detailFormatter(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}
// 初始化input特效
function initMaterialInput() {
    $('form input,form textarea').each(function () {
        if ($(this).val() != '') {
            $(this).parent().find('label').addClass('active');
        }
    });
}

//时间戳转化成日期时间
function timeStampToDateTime(timeStamp) {
    var dateVal = timeStamp + "";
    if (timeStamp != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}

function showTips(tip){
    $.confirm({
        theme: 'dark',
        animation: 'rotateX',
        closeAnimation: 'rotateX',
        title: false,
        content: tip,
        buttons: {
            confirm: {
                text: '确认',
                btnClass: 'waves-effect waves-button waves-light'
            }
        }
    });
}