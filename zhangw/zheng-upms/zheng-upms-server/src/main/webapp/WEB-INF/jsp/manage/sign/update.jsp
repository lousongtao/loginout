<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="updateDialog" class="crudDialog">
    <form id="updateForm" method="post">
        <input type="hidden", name="signId", id="signId", value="${mcUserSign.signId}">
        <div class="form-group">
            <label for="name">Name</label>
            <input id="name" type="text" class="form-control" name="uName" maxlength="20" value="${mcUserSign.uName}" readonly>
        </div>
        <div class="form-group">
            <label for="signTime">Sign Time</label>
            <input id="signTime" class="form-control" name="signTime" value="">
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-info">
                <input id="signin" type="radio" name="signType" value="0" <c:if test="${mcUserSign.signType == 0}"> checked </c:if> >
                <label for="signin">Sign In</label>
            </div>
            <div class="radio radio-inline radio-danger">
                <input id="signout" type="radio" name="signType" value="1" <c:if test="${mcUserSign.signType == 1}"> checked </c:if> >
                <label for="signout">Sign Out</label>
            </div>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">Save</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">Cancel</a>
        </div>

    </form>
</div>
<script>

    // 把格式Tue Aug 21 13:36:51 CST 2018 转化为2018-08-21 13:36:51. 使用JavaScript的Date对象, 总是出现延后6小时的现象, 不知道如何解决
    function getDateCSTTime(s){
        var months = new Array("", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");//第一位置是0, 补空
        var parts = s.split(" ");

        var month = 0;
        for ( var i = 0; i<months.length; i++){
            if (months[i] ===parts[1]){
                month = i;
                break;
            }
        }
        if (month < 10){
            return parts[5] + "-0" + month + "-" + parts[2] + " " + parts[3];
        } else {
            return parts[5] + "-" + month + "-" + parts[2] + " " + parts[3];
        }
    }
    //datetimepicker日期选择控件
    $(function () {
        var signTime = getDateCSTTime("${mcUserSign.signTime}");
        $('#signTime').val(signTime);
        var signDate = signTime.split(" ")[0];

        //格式化时间控件
        $('#signTime').datetimepicker({
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startDate: signDate,
            startView: 'day',
            minView: 'hour',
            maxView: 'month',
            format: 'yyyy-mm-dd hh:ii:ss',
            forceParse: 0
        });
        // $('signTime').datetimepicker('setStartDate', signTime);
    })

    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/sign/update/${mcUserSign.signId}',
            data: $('#updateForm').serialize(),
            beforeSend: function () {
                if ($('#name').val() == '') {
                    $('#name').focus();
                    return false;
                }
            },
            success: function (result) {
                if (result.code != 1) {
                    if (result.data instanceof Array) {
                        $.each(result.data, function (index, value) {
                            $.confirm({
                                theme: 'dark',
                                animation: 'rotateX',
                                closeAnimation: 'rotateX',
                                title: false,
                                content: value.errorMsg,
                                buttons: {
                                    confirm: {
                                        text: 'Yes',
                                        btnClass: 'waves-effect waves-button waves-light'
                                    }
                                }
                            });
                        });
                    } else {
                        $.confirm({
                            theme: 'dark',
                            animation: 'rotateX',
                            closeAnimation: 'rotateX',
                            title: false,
                            content: result.data,
                            buttons: {
                                confirm: {
                                    text: 'Yes',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                    }
                } else {
                    updateDialog.close();
                    $table.bootstrapTable('refresh');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.confirm({
                    theme: 'dark',
                    animation: 'rotateX',
                    closeAnimation: 'rotateX',
                    title: false,
                    content: textStatus,
                    buttons: {
                        confirm: {
                            text: 'Yes',
                            btnClass: 'waves-effect waves-button waves-light'
                        }
                    }
                });
            }
        });
    }
</script>