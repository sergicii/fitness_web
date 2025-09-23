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
        <h1>Panel de Control Fitness</h1>
        <p>Gestión Integral de Sesiones de Entrenamiento</p>
    </header>
    <div class="options-container">
        <header>
            <h2>Sesiones Programadas</h2>
            <p><span id="sessions-count">Cargando...</span> sesiones programadas</p>
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
            <%-- El action apunta a la URL de tu servlet y el método es POST --%>
            <form class="option" action="operation" method="post">
                <h3>
                    Crear Sesión de Entrenamiento
                </h3>

                <input type="hidden" name="action" value="create">

                <label>Instalación:
                    <select name="siteId" required>
                        <option value="" disabled selected>-- Selecciona una instalación --</option>
                        <c:forEach var="site" items="${sites}">
                            <option value="${site.id}">${site.name}</option>
                        </c:forEach>
                    </select>
                </label>

                <label>Entrenamiento:
                    <select name="trainingId" required>
                        <option value="" disabled selected>-- Selecciona un entrenamiento --</option>
                        <c:forEach var="training" items="${trainings}">
                            <option value="${training.id}">${training.name}</option>
                        </c:forEach>
                    </select>
                </label>

                <label>Entrenador:
                    <select name="trainerId" required>
                        <option value="" disabled selected>-- Selecciona un entrenador --</option>
                        <c:forEach var="trainer" items="${trainers}">
                            <option value="${trainer.id}">${trainer.firstname} ${trainer.lastname}</option>
                        </c:forEach>
                    </select>
                </label>

                <label>Tipo:
                    <select name="sessionType" required>
                        <option value="" disabled selected>-- Selecciona un tipo --</option>
                        <%-- Generamos dinámicamente las opciones desde el enum SessionType --%>
                        <c:forEach var="type" items="${sessionTypes}">
                            <%-- El 'value' es el nombre del enum (ej. GRUPAL), que es lo que espera SessionType.valueOf() --%>
                            <option value="${type}">${type.value}</option>
                        </c:forEach>
                    </select>
                </label>

                <label>
                    Fecha y hora de inicio:
                    <input type="datetime-local" name="start" required>
                </label>

                <label>
                    Máximo de participantes:
                    <input type="number" name="maxParticipants" min="1" required>
                </label>

                <button type="submit">Crear Sesión</button>
            </form>

             <c:forEach var="session" items="${sessions}">
                <form class="option" action="operation" method="post">
                    <input type="hidden" name="action" value="delete">
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
                    <button type="submit" class="btn-delete">Eliminar Sesión</button>
                </form>
            </c:forEach>
        </div>
    </div>

    <div class="options-container">
        <header>
            <h2>Instalaciones</h2>
            <p><span id="sites-count">Cargando...</span> ubicaciones disponibles</p>
        </header>

        <div class="options-list" id="sites-list">
            <c:forEach var="site" items="${sites}">
                <article class="option" data-type="site">
                    <h3>
                        <c:out value="${site.name}"/>
                    </h3>
                    <p>Dirección: <c:out value="${site.address}"/></p>
                    <p>Tipo: <c:out value="${site.type.getValue()}"/></p>
                    <p>Capacidad: <c:out value="${site.capacity}"/> personas</p>
                    <p>Horario: <c:out value="${site.opening}"/> - <c:out value="${site.closing}"/></p>
                </article>
            </c:forEach>
        </div>
    </div>

    <div class="options-container">
        <header>
            <h2>Entrenamiento</h2>
            <p><span id="trainings-count">Cargando...</span> entrenamientos</p>
        </header>

        <div class="options-list" id="trainings-list">
            <c:forEach var="training" items="${trainings}">
                <article class="option" data-type="training">
                    <h3><c:out value="${training.name}"/></h3>
                    <p>Descripción: <c:out value="${training.description}"/></p>
                    <p>Duración: <c:out value="${training.duration}"/> minutos</p>
                    <p>Capacidad: <c:out value="${training.maxParticipants}"/> participantes</p>
                </article>
            </c:forEach>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const sitesCount = document.querySelectorAll('#sites-list .option').length;
            const trainingsCount = document.querySelectorAll('#trainings-list .option').length;
            const sessionsCount = document.querySelectorAll('#session-list .option').length;

            document.getElementById('sites-count').textContent = sitesCount.toString();
            document.getElementById('trainings-count').textContent = trainingsCount.toString();
            document.getElementById('sessions-count').textContent = (sessionsCount - 1).toString();
        });
    </script>

</body>