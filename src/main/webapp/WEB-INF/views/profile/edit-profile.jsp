<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit profile</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/_header.jsp"/>
<c:if test="${not empty message && status != null}">
    <c:choose>
        <c:when test="${status == 'SUCCESS'}">
            <div class="alert alert-primary" role="alert">
                <c:out value="${message}"/>
            </div>
        </c:when>
        <c:when test="${status == 'ERROR'}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${message}"/>
            </div>
        </c:when>
    </c:choose>
</c:if>

<div class="container">

    <form class="form-group" action="${pageContext.request.contextPath}/profile/edit" method="post"
          enctype="multipart/form-data">
        <div class="row mb-5">
            <div class="col col-3">

                <label for="user-photo-input">
                    <input hidden="hidden" type="file" id="user-photo-input" name="user-photo" accept="image/*">
                    <c:choose>
                        <c:when test="${currentFileId == null}">
                            <img src="${pageContext.request.contextPath}/resources/img/icons/userDefaultImage.png"
                                 alt="Default Photo" class="img-fluid h-100" id="edit-user-photo">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/files/${currentFileId}"
                                 alt="User Photo" class="img-fluid h-100" id="edit-user-photo">
                        </c:otherwise>
                    </c:choose>
                    <div class="row">Edit Photo</div>
                </label>
            </div>
            <div class="col">
                <div class="row mb-3">
                    <div class="col-sm-12">
                        <label for="first-name" class="col-form-label">
                            First Name
                            <span class="text-danger">*</span>
                        </label>
                        <input id="first-name" name="first-name" type="text" class="form-control" value="${firstName}"
                               required>
                    </div>
                </div>

                <div class="row  mb-3">
                    <div class="col-sm-12">
                        <label for="last-name">
                            Last Name
                            <span class="text-danger">*</span>
                        </label>
                        <input id="last-name" name="last-name" type="text" class="form-control" value="${lastName}"
                               required>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <label for="gender">Sex</label>
                        <select id="gender" name="gender" class="form-control">
                            <option value="FEMALE" ${gender eq 'FEMALE' ? 'selected' : ''}>Female</option>
                            <option value="MALE" ${gender eq 'MALE' ? 'selected' : ''}>Male</option>
                        </select>

                    </div>


                    <div class="col-sm-6">
                        <label for="date">Date of Birth</label>
                        <input type="date" id="date" name="date-of-birth" class="form-control" value="${birthday}">

                    </div>
                </div>
            </div>
        </div>

        <div class="row  mb-3">
            <label for="about">About</label>
            <input id="about" name="about" type="text" class="form-control" value="${about}">
        </div>

        <div class="row">
            <button type="submit" class="btn btn-primary col-md-2">Edit</button>
            <a href="${pageContext.request.contextPath}/" class="btn btn-light col-md-2 offset-md-8">Cancel</a>
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/views/common/_footer.jsp"/>
<br/>
<script>
    const fileToBase64 = file => new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);
    });

    document.getElementById("user-photo-input").addEventListener("change", async (event) => {
        // Access the list of selected files
        const fileList = event.target.files;
        if (fileList.length > 0) {
            const file = event.target.files[0];
            if (!file) return;
            try {
                const base64StringImage = await fileToBase64(file);
                document.getElementById("edit-user-photo").setAttribute("src", base64StringImage)
            } catch (error) {
                console.error("Error converting file:", error);
            }
        }
    });
</script>
</body>
</html>
