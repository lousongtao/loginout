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
        <input type="hidden" name="startDate" id="startDate" value="${startDate}">
        <input type="hidden" name="branchId" id="branchId" value="${branchId}">
        <div class="form-group">
            <label for="name">Name</label>
            <input id="name" type="text" class="form-control" name="name" maxlength="20">
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">Save</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="createDialog.close();">Cancel</a>
        </div>
    </form>
</div>
<script>

    function createSubmit() {
        $.ajax({
            type: 'post',
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            url: '${basePath}/manage/scheduling/createScheduleTemplate',
            data: $('#createForm').serialize(),
            beforeSend: function () {
                if ($('#name').val() == '') {
                    $('#name').focus();
                    return false;
                }
            },
            success: function (result) {
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
                newTemplateDialog.close();
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