<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="createDialog" class="crudDialog">
    <form id="createForm" method="post">
        <div class="form-group">
            <select id="uId" name="uId" style="width: 100%">
                <option value="">Choose One</option>
                <c:forEach var="staff" items="${staffs}">
                    <option value="${staff.userId}">${staff.realname}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="signTime">Sign Time</label>
            <input id="signTime" class="form-control" name="signTime" value="">
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-info">
                <input id="signin" type="radio" name="signType" value="0" checked >
                <label for="signin">Sign In</label>
            </div>
            <div class="radio radio-inline radio-danger">
                <input id="signout" type="radio" name="signType" value="1"  >
                <label for="signout">Sign Out</label>
            </div>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">Save</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="createDialog.close();">Cancel</a>
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
        var nowTime = new Date();
        var startDate = new Date(nowTime.getTime() - 7 * 24 * 3600 * 1000);
        $('#signTime').val('');

        //格式化时间控件
        $('#signTime').datetimepicker({
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startDate: startDate,
            startView: 'day',
            minView: 'hour',
            maxView: 'month',
            format: 'yyyy-mm-dd hh:ii:ss',
            forceParse: 0
        });
    })

    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/sign/supplySign',
            data: $('#createForm').serialize(),
            beforeSend: function () {
                if ($('#uId').val() == '') {
                    $('#uId').focus();
                    return false;
                }
                if ($('#signTime').val() == ''){
                    $('#signTime').focus();
                    return false;
                }
            },
            success: function (result) {
                createDialog.close();
                $table.bootstrapTable('refresh');
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