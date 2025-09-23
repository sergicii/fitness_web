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
        <h1>Panel de Entrenadores</h1>
        <p>Gestión Integral de Sesiones de Entrenamiento</p>
    </header>
    <div class="options-container">
        <header>
            <h2>Mis Sesiones Programadas</h2>
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
        <%-- ===================== FIN DEL BLOQUE DE MENSAJES ===================== --%>

        <div class="options-list" id="session-list">
            <c:forEach var="session" items="${sessions}">
                <form class="option" action="trainer" method="post">
                        <%-- Acción y ID de la sesión que se está modificando --%>
                    <input type="hidden" name="action" value="updateSessionDetails">
                    <input type="hidden" name="sessionId" value="${session.id}">

                    <h3>Sesión ID: <c:out value="${session.id}"/></h3>
                    <p>Instalación: <c:out value="${session.site.name}"/></p>
                    <p>Tipo: <c:out value="${session.type.getValue()}"/></p>
                    <p>Inicio: <c:out value="${session.start}"/></p>
                    <p>Fin: <c:out value="${session.end}" /></p>
                    <p>Participantes: <c:out value="${fn:length(session.reservations)}"/> / <c:out value="${session.maxParticipants}"/></p>

                        <%-- 1. Input para el reporte de la sesión (usamos textarea para más espacio) --%>
                    <label><b>Reporte de la Sesión:</b>
                        <textarea name="sessionReport" rows="3" placeholder="Escribe un resumen de la sesión, incidentes, etc.">${session.sessionReport}</textarea>
                    </label>

                        <%-- 2. Lista de reservaciones con el select para el estado --%>
                    <h4>Asistencia de Clientes</h4>
                    <ul class="reservations-list">
                        <c:if test="${empty session.reservations}">
                            <li>No hay clientes inscritos en esta sesión.</li>
                        </c:if>

                        <c:forEach var="reservation" items="${session.reservations}">
                            <li>
                                <span><c:out value="${reservation.client.firstname} ${reservation.client.lastname}" /></span>
                                <select name="reservationStatus_${reservation.id}">
                                    <option value="" disabled selected>${reservation.state.value}</option>
                                    <c:forEach var="status" items="${reservationStates}">
                                        <option value="${status}">
                                                ${status.value}
                                        </option>
                                    </c:forEach>
                                </select>
                            </li>
                        </c:forEach>
                    </ul>

                    <button type="submit">Guardar Cambios</button>
                </form>
            </c:forEach>
        </div>
    </div>

</body>