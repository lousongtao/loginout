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
        <div class="form-group">
            <label for="planName">班次名称</label>
            <input id="planName" type="text" class="form-control" name="planName" maxlength="20"
                   value="${mcShift.planName}" readonly>
        </div>
        <div class="form-group">
            <label for="color">颜色</label>
            <input id="color" type="color" class="form-control" name="color" maxlength="20"
                   value="${mcShift.color}">
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-info">
                <input id="type_1" type="radio" name="type" value="0" <c:if test="${mcShift.type==0}">checked</c:if>>
                <label for="type_1">工作 </label>
            </div>
            <div class="radio radio-inline radio-danger">
                <input id="type_0" type="radio" name="type" value="1" <c:if test="${mcShift.type==1}">checked</c:if>>
                <label for="type_0">其他 </label>
            </div>
        </div>
        <div class="form-group">
            <label for="periodTime">起止时间</label>
            <input id="periodTime" type="text" class="form-control" name="periodTime" maxlength="20"
                   value="${mcShift.periodTime}">
        </div>
        <div class="form-group">
            <label for="totalTime">总时长</label>
            <input id="totalTime" type="text" class="form-control" name="totalTime" maxlength="20"
                   value="${mcShift.totalTime}">
        </div>
        <div class="form-group">
            <label for="mark">备注</label>
            <input id="mark" type="text" class="form-control" name="mark" maxlength="100"
                   value="${mcShift.mark}">
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">保存</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>
    //datetimepicker日期选择控件
    $(function () {
        //格式化时间控件

        $('#startTime').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 1,
            minView: 0,
            maxView: 1,
            format: 'hh:ii',
            forceParse: 0
        }).on('changeDate', function (ev) {
            planTimeChange(ev);
        });
        $('#endTime').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 1,
            minView: 0,
            maxView: 1,
            format: 'hh:ii',
            forceParse: 0
        }).on('changeDate', function (ev) {
            planTimeChange(ev);
        });
    })

    //计算总时间
    function planTimeChange(ev) {
        var sTime = $('#startTime').val();
        var eTime = $('#endTime').val();
        $('#periodTime').focus();
        $('#periodTime').val(sTime + "-" + eTime);

        if (sTime) {
            var sTime0 = sTime.split(":")[0];
            var sTime1 = (sTime.split(":")[1] / 60).toFixed(1);
            sTime = Number(sTime0) + Number(sTime1);
        }
        if (eTime) {
            var eTime0 = eTime.split(":")[0];
            var eTime1 = (eTime.split(":")[1] / 60).toFixed(1);
            eTime = Number(eTime0) + Number(eTime1);
        }
        var totalTime = Number((eTime - sTime).toFixed(1));
        $('#totalTime').focus();
        if (totalTime < 0) {
            $('#totalTime').val(Number(totalTime)+24);
        }else{
            $('#totalTime').val(totalTime);
        }
    }

    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/shift/update/${mcShift.id}',
            data: $('#updateForm').serialize(),
            beforeSend: function () {
                if ($('#planName').val() == '') {
                    $('#planName').focus();
                    return false;
                }
                if ($('#color').val() == '') {
                    $('#color').focus();
                    return false;
                }
                if ($('#periodTime').val() == '') {
                    $('#periodTime').focus();
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
                                        text: '确认',
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
                            content: result.data.errorMsg,
                            buttons: {
                                confirm: {
                                    text: '确认',
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
                            text: '确认',
                            btnClass: 'waves-effect waves-button waves-light'
                        }
                    }
                });
            }
        });
    }

</script>