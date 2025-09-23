<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<html>
<head>
    <title>Fitness | Organizar Sesiones de Entrenamiento</title>
    <link href="styles/global.css" rel="stylesheet">
</head>
<body>
<header>
    <h1>Panel de Clientes</h1>
    <p>Gestión Integral de Reservas y Reclamos</p>
</header>
<div class="options-container">
    <header>
        <h2>Sesiones Programadas</h2>
        <p><span id="sessions-count">Cargando...</span> sesiones programadas</p>
    </header>

    <div class="messages-container">
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-success">
                <c:out value="${sessionScope.message}"/>
            </div>
            <c:remove var="message" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-error">
                <c:out value="${sessionScope.error}"/>
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>
    </div>

    <div class="options-list" id="session-list">
        <c:forEach var="session" items="${sessions}">
            <form class="option" action="client" method="post">
                <input type="hidden" name="action" value="reserva">
                <input type="hidden" name="sessionId" value="${session.id}">

                <h3>Sesión ID: <c:out value="${session.id}"/></h3>
                <p>Participantes:
                    <c:out value="${fn:length(session.reservations)}"/> / <c:out value="${session.maxParticipants}"/>
                </p>
                <p>Instalación: <c:out value="${session.site.name}"/></p>
                <p>Tipo: <c:out value="${session.type.getValue()}"/></p>
                <p>Entrenador:
                    <c:out value="${session.trainer.firstname}"/>
                    <c:out value="${session.trainer.lastname}" />
                </p>
                <p>Inicio: <c:out value="${session.start}"/></p>
                <p>Fin: <c:out value="${session.end}" /></p>
                <button type="submit" class="btn-delete">Reservar</button>
            </form>
        </c:forEach>
    </div>
</div>

<div class="options-container">
    <header>
        <h2>Mis Reservas</h2>
        <p><span id="sessions-count">Cargando...</span> reservas</p>
    </header>

    <div class="messages-container">
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-success">
                <c:out value="${sessionScope.message}"/>
            </div>
            <c:remove var="message" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-error">
                <c:out value="${sessionScope.error}"/>
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>
    </div>

    <div class="options-list" id="session-list">
        <c:forEach var="reservation" items="${reservations}">
            <div class="option">
                <h3>Sesión ID: <c:out value="${reservation.id}"/></h3>
                <p>Instalación: <c:out value="${reservation.session.site.name}"/></p>
                <p>Entrenador:
                    <c:out value="${reservation.session.trainer.firstname}"/>
                    <c:out value="${reservation.session.trainer.lastname}" />
                </p>
                <p>Inicio: <c:out value="${reservation.session.start}"/></p>
                <p>Fin: <c:out value="${reservation.session.end}" /></p>
                <p>Estado: <c:out value="${reservation.state.value}" /></p>
            </div>
        </c:forEach>
    </div>
</div>

<div class="options-container">
    <header>
        <h2>Mis Reclamos</h2>
        <p><span id="sessions-count">Cargando...</span> reclamos</p>
    </header>

    <%-- ================================================================== --%>
    <%--      AÑADE ESTE BLOQUE PARA MOSTRAR LOS MENSAJES DE LA SESIÓN      --%>
    <%-- ================================================================== --%>
    <div class="messages-container">
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-success">
                <c:out value="${sessionScope.message}"/>
            </div>
            <c:remove var="message" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-error">
                <c:out value="${sessionScope.error}"/>
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>
    </div>
    <%-- ===================== FIN DEL BLOQUE DE MENSAJES ===================== --%>

    <div class="options-list" id="session-list">
        <form class="option" action="client" method="post">
            <h3>
                Crear Reclamo
            </h3>

            <input type="hidden" name="action" value="createIncident">

            <label>
                Título:
                <input name="title" required>
            </label>

            <label>
                Descripción:
                <textarea name="description" rows="4" required></textarea>
            </label>

            <button type="submit">Enviar Reclamo</button>
        </form>

        <c:forEach var="incident" items="${incidents}">
            <div class="option">
                <h3>Título: <c:out value="${incident.title}"/></h3>
                <p>Descripción: <c:out value="${incident.description}"/></p>
                <p>Fecha: <c:out value="${incident.date}"/></p>
            </div>
        </c:forEach>
    </div>
</div>

</body>
