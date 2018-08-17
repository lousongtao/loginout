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
        <input id="scheduleId" type="hidden" value="${mcSchedulePlan.id}">
        <div class="form-group">
            <label for="schedulingDate">Date</label>
            <fmt:formatDate value="${mcSchedulePlan.schedulingDate}" pattern="yyyy-MM-dd" var="schedulingDate"/>
            <input id="schedulingDate" type="text" class="form-control" name="schedulingDate" maxlength="20"
                   value="${schedulingDate}" readonly>
        </div>
        <div class="form-group">
            <label for="uName">Name</label>
            <input id="uName" type="text" class="form-control" name="uName" maxlength="20"
                   value="${mcSchedulePlan.uName}" readonly>
        </div>
        <div class="form-group">
            <label for="periodTime">Scheduling</label>
            <input id="periodTime" type="text" class="form-control" name="phone" maxlength="20"
                   value="${mcSchedulePlan.periodTime}" readonly>
        </div>
        <div class="form-group">
            <label for="totalTime">Total Time(hour)</label>
            <input id="totalTime" type="text" class="form-control" name="totalTime"
                   value="${mcSchedulePlan.totalTime}" readonly>
        </div>
        <div class="form-group">
            <label for="estimatePay">Estimate Pay($)</label>
            <input id="estimatePay" type="text" class="form-control" name="estimatePay"
                   value="${(mcSchedulePlan.estimatePay)/100}" readonly>
        </div>
        <div class="form-group">
            <label for="userSignFirst">First Sign Record</label>
            <fmt:formatDate value="${userSignFirst}" pattern="HH:ss:mm" var="firstSignTime"/>
            <input id="userSignFirst" type="text" class="form-control" name="userSignFirst"
                   value="${firstSignTime}" readonly>
        </div>
        <div class="form-group">
            <label for="userSignLast">Last Sign Record</label>
            <fmt:formatDate value="${userSignLast}" pattern="HH:ss:mm" var="lastSignTime"/>
            <input id="userSignLast" type="text" class="form-control" name="userSignLast"
                   value="${lastSignTime}" readonly>
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-danger">
                <input id="result_0" type="radio" name="result" value="0"
                       <c:if test="${mcSchedulePlan.result==0}">checked</c:if>>
                <label for="result_0"><a href="javascript:;">??</a></label>
            </div>
            <div class="radio radio-inline radio-success">
                <input id="result_1" type="radio" name="result" value="1"
                       <c:if test="${mcSchedulePlan.result==1}">checked</c:if>>
                <label for="result_1"><a href="javascript:;" style="background-color: #00B83F"><i
                        class="zmdi zmdi-check zmdi-hc-fw"></i></a></label>
            </div>
            <div class="radio radio-inline radio-danger">
                <input id="result_2" type="radio" name="result" value="2"
                       <c:if test="${mcSchedulePlan.result==2}">checked</c:if>>
                <label for="result_2"><a href="javascript:;" style="background-color: red"><i
                        class="zmdi zmdi-close zmdi-hc-fw"></i></a></label>
            </div>
        </div>
    </form>
</div>
<script>
    $(function () {
        $("input[name=result]").change(function () {
            var id = $("#scheduleId").val();
            var result = $("input[name='result']:checked").val();
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/attendance/updateResult',
                data: {
                    id: id, result: result
                },
                beforeSend: function () {
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
                                content: result.data.errorMsg,
                                buttons: {
                                    confirm: {
                                        text: 'Yes',
                                        btnClass: 'waves-effect waves-button waves-light'
                                    }
                                }
                            });
                        }
                    } else {
                        showTips("The result has been revised.")
                        setTimeout(function () {
                            cellDialog.close();
                            $table.bootstrapTable('refresh');
                        }, 2000)
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

        });
    });

</script>