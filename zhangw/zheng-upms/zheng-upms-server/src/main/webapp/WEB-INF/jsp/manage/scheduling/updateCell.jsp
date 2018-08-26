<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="updateCellDialog" class="crudDialog">
    <form id="createForm" method="post">
        <input type="hidden" name="id" id="cellId" value="${cellData.id}">
        <input type="hidden" name="schedulingDate" value="${schedulingDate}">
        <input type="hidden" name="groupId" value="${groupId}">
        <div class="form-group">
            <select id="uId" name="uId" style="width: 100%">
                <option value="">Choose One</option>
                <c:forEach var="groupUser" items="${groupUsers}">
                    <option value="${groupUser.userId}"
                            <c:if test="${groupUser.userId==cellData.uId}">selected="selected"</c:if>
                    >${groupUser.username}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="startTime">Start Time</label>
            <input class="form-control" id='startTime' name="startTime" value="${cellData.periodTime}"/>
        </div>
        <div class="form-group">
            <label for="endTime">End Time</label>
            <input name="endTime" id="endTime" class="form-control" value="${cellData.periodTime}">
        </div>
        <div class="form-group">
            <label for="periodTime">Period Time</label>
            <input id="periodTime" type="text" class="form-control" name="periodTime"
                   value="${cellData.periodTime} readonly"
                   readonly>
        </div>
        <div class="form-group">
            <label for="totalTime">Total Time(hour)</label>
            <input id="totalTime" type="text" class="form-control" name="totalTime"
                   value="${cellData.totalTime}" readonly>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" style="color: red" href="javascript:;"
               onclick="deleteCell();">Delete</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">Save</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateCellDialog.close();">Cancel</a>
        </div>
    </form>
</div>
<script>
    //datetimepicker日期选择控件
    $(function () {
        //日期插件 赋值
        var start = $('#startTime').val();
        var end = $('#endTime').val();
        if (start && end) {
            $('#startTime').val(start.split("-")[0]);
            $('#endTime').val(start.split("-")[1]);
        }
        //初始化select 插件
        $('select').select2({
            placeholder: 'Choose One',
            allowClear: true
        });
        planTimeChange();
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
//        $('#totalTime').focus();
        if (totalTime < 0) {
            $('#totalTime').val(Number(totalTime) + 24);
        } else {
            $('#totalTime').val(totalTime);
        }
    }

    function deleteCell() {
        deleteDialog = $.confirm({
            type: 'red',
            animationSpeed: 300,
            title: false,
            content: 'Do you confirm to delete it？',
            buttons: {
                confirm: {
                    text: 'Confirm',
                    btnClass: 'waves-effect waves-button',
                    action: function () {
                        $.ajax({
                            type: 'get',
                            url: '${basePath}/manage/scheduling/deleteCell/' + $("#cellId").val(),
                            beforeSend: function () {
                                if ($('#cellId').val() == '') {
                                    updateCellDialog.close();
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
                                                        text: 'Confirm',
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
                                            content: result.data.errorMsg || result.data,
                                            buttons: {
                                                confirm: {
                                                    text: 'Yes',
                                                    btnClass: 'waves-effect waves-button waves-light'
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    updateCellDialog.close();
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
                },
                cancel: {
                    text: 'Cancel',
                    btnClass: 'waves-effect waves-button'
                }
            }
        });

    }

    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/scheduling/updateCell',
            data: $('#createForm').serialize(),
            beforeSend: function () {
                if ($('#uId').val() == '') {
                    $('#uId').focus();
                    return false;
                }
                if ($('#startTime').val() == '') {
                    $('#startTime').focus();
                    return false;
                }
                if ($('#endTime').val() == '') {
                    $('#endTime').focus();
                    return false;
                }
                if ($('#periodTime').val() == '') {
                    $('#startTime').focus();
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
                                        text: 'Confirm',
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
                            content: result.data.errorMsg || result.data,
                            buttons: {
                                confirm: {
                                    text: 'Yes',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                    }
                } else {
                    updateCellDialog.close();
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