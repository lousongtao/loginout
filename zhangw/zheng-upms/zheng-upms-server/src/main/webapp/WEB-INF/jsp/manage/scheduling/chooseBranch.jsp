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
            <select id="branchId" name="branchIdId" style="width: 100%">
                <option value="">Choose One Branch</option>
                <c:forEach var="branch" items="${branches}">
                    <option value="${branch.id}" >${branch.branchName} </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="choose();">Choose</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">Cancel</a>
        </div>

    </form>
</div>
<script>
    function choose() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/branch/index/' + $('#branchId').val(),
            data: $('#updateForm').serialize(),
            beforeSend: function () {
                if ($('#branchId').val() == '') {
                    $('#branchId').focus();
                    return false;
                }
            },
            success: function (result) {
                updateDialog.close();

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