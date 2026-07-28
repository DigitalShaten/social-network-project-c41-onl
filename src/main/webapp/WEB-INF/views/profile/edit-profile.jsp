<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/_header.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h4 class="mb-3">Редактирование профиля</h4>

<c:if test="${not empty message && status != null}">
    <c:choose>
        <c:when test="${status == 'SUCCESS'}">
            <div class="alert alert-primary" role="alert"><c:out value="${message}"/></div>
        </c:when>
        <c:when test="${status == 'ERROR'}">
            <div class="alert alert-danger" role="alert"><c:out value="${message}"/></div>
        </c:when>
    </c:choose>
</c:if>

<div class="card shadow-sm">
    <div class="card-body">
        <form action="${ctx}/profile/edit" method="post" enctype="multipart/form-data">
            <div class="row mb-4">
                <div class="col-md-3 text-center">
                    <label for="user-photo-input" style="cursor:pointer;">
                        <input hidden type="file" id="user-photo-input" name="user-photo" accept="image/*">
                        <c:choose>
                            <c:when test="${currentFileId == null}">
                                <img src="${ctx}/resources/img/icons/userDefaultImage.png" alt="avatar"
                                     class="rounded-circle border" id="edit-user-photo"
                                     style="width:120px;height:120px;object-fit:cover;">
                            </c:when>
                            <c:otherwise>
                                <img src="${ctx}/files/${currentFileId}" alt="avatar"
                                     class="rounded-circle border" id="edit-user-photo"
                                     style="width:120px;height:120px;object-fit:cover;">
                            </c:otherwise>
                        </c:choose>
                        <div class="text-secondary small mt-2">Сменить фото</div>
                    </label>
                </div>
                <div class="col-md-9">
                    <div class="mb-3">
                        <label for="first-name" class="form-label">First Name <span class="text-danger">*</span></label>
                        <input id="first-name" name="first-name" type="text" class="form-control" value="${firstName}" required>
                    </div>
                    <div class="mb-3">
                        <label for="last-name" class="form-label">Last Name <span class="text-danger">*</span></label>
                        <input id="last-name" name="last-name" type="text" class="form-control" value="${lastName}" required>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 mb-3">
                            <label for="gender" class="form-label">Sex</label>
                            <select id="gender" name="gender" class="form-control">
                                <option value="FEMALE" ${gender eq 'FEMALE' ? 'selected' : ''}>Female</option>
                                <option value="MALE" ${gender eq 'MALE' ? 'selected' : ''}>Male</option>
                            </select>
                        </div>
                        <div class="col-sm-6 mb-3">
                            <label for="date" class="form-label">Date of Birth</label>
                            <input type="date" id="date" name="date-of-birth" class="form-control" value="${birthday}">
                        </div>
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <label for="about" class="form-label">About</label>
                <input id="about" name="about" type="text" class="form-control" value="${about}">
            </div>

            <div class="d-flex gap-2">
                <button type="submit" class="btn text-white" style="background-color:#5C7CFA;">Сохранить</button>
                <a href="${ctx}/profile" class="btn btn-light">Отмена</a>
            </div>
        </form>
    </div>
</div>

<script>
    const fileToBase64 = file => new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);
    });
    document.getElementById("user-photo-input").addEventListener("change", async (event) => {
        const file = event.target.files[0];
        if (!file) return;
        try {
            document.getElementById("edit-user-photo").setAttribute("src", await fileToBase64(file));
        } catch (error) {
            console.error("Error converting file:", error);
        }
    });
</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp"/>
