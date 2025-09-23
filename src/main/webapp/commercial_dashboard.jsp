<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<html>
<head>
    <title>Fitness | Area Comercial</title>
    <link href="styles/global.css" rel="stylesheet">
</head>
<body>
    <header>
        <h1>Panel de Area Comercial</h1>
        <p>Gestión Membresías, Contratos y Clientes</p>
    </header>

    <div class="options-container">
        <header>
            <h2>Membresías Contratadas</h2>
            <p><span id="contracted-count">Cargando...</span> contratadas</p>
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

        <div class="options-list" id="contracted-list">
            <%-- ================================================================== --%>
            <%--     FORMULARIO PARA CONTRATAR UNA MEMBRESÍA (LÓGICA CORREGIDA)     --%>
            <%-- ================================================================== --%>
            <form class="option" action="commercial" method="post">
                <h3>Contratar Membresía</h3>

                <input type="hidden" name="action" value="contractMembership">

                <label>Cliente:
                    <select name="clientId" required>
                        <option value="" disabled selected>-- Selecciona un cliente --</option>
                        <c:forEach var="client" items="${clients}">
                            <option value="${client.id}">${client.firstname} ${client.lastname} (${client.dni})</option>
                        </c:forEach>
                    </select>
                </label>

                <label>Membresía:
                    <select name="membershipId" required>
                        <option value="" disabled selected>-- Selecciona una membresía --</option>
                        <c:forEach var="membership" items="${memberships}">
                            <option value="${membership.id}">${membership.category} - S/.${membership.price}</option>
                        </c:forEach>
                    </select>
                </label>

                <hr style="width: 100%; border-top: 1px solid #ccc; margin: 15px 0;">
                <p style="font-size: 0.9em; text-align: center; margin-bottom: 10px;">
                    <b>Cuenta de Usuario</b><br>
                </p>

                <label>Email:
                    <input type="email" name="email" required>
                </label>
                <label>Contraseña:
                    <input type="password" name="password" required>
                </label>
                <%-- ================================================================== --%>

                <label>Estado del Contrato:
                    <select name="state" required>
                        <option value="" disabled selected>-- Selecciona un estado --</option>
                        <c:forEach var="contractedState" items="${contractedStates}">
                            <option value="${contractedState}">${contractedState.value}</option>
                        </c:forEach>
                    </select>
                </label>

                <button type="submit">Añadir contratación</button>
            </form>

            <c:forEach var="contrac" items="${contracted}">
                <article class="option">
                    <h3>Contrato ID: <c:out value="${contrac.id}"/></h3>
                    <p>Nombres y apellidos: <c:out value="${contrac.client.firstname}"/>
                        <c:out value="${contrac.client.lastname}" /></p>
                    <p>DNI: <c:out value="${contrac.client.dni}"/></p>
                    <p>Membresía: <c:out value="${contrac.membership.category}"/></p>
                    <p>Inicio: <c:out value="${contrac.start}"/></p>
                    <p>Fin: <c:out value="${contrac.end}"/></p>
                    <p>Estado: <c:out value="${contrac.state}"/></p>
                </article>
            </c:forEach>
        </div>
    </div>

    <div class="options-container">
        <header>
            <h2>Clientes</h2>
            <p><span id="clients-count">Cargando...</span> clientes</p>
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

        <div class="options-list" id="client-list">
            <form class="option" action="commercial" method="post">
                <h3>
                    Crear Nuevo Cliente
                </h3>

                <input type="hidden" name="action" value="createClient">

                <label>Nombres:
                    <input type="text" name="firstname" required>
                </label>
                <label>Apellidos:
                    <input type="text" name="lastname" required>
                </label>
                <label>DNI:
                    <input type="text" name="dni" maxlength="8" required>
                </label>
                <label>Celular:
                    <input type="text" name="numberPhone" maxlength="9">
                </label>
                <label>Dirección:
                    <input type="text" name="address">
                </label>
                <label>Edad:
                    <input type="number" name="age" min="13" required>
                </label>

                <label>Sexo:
                    <select name="gender" required>
                        <option value="" disabled selected>-- Selecciona un sexo --</option>
                        <c:forEach var="gender" items="${genders}">
                            <option value="${gender}">${gender.value}</option>
                        </c:forEach>
                    </select>
                </label>

                <button type="submit">Crear Cliente</button>
            </form>

            <c:forEach var="client" items="${clients}">
                <article class="option">
                    <h3>Client ID: <c:out value="${client.id}"/></h3>
                    <p>Nombres: <c:out value="${client.firstname}"/></p>
                    <p>Apellidos: <c:out value="${client.lastname}"/></p>
                    <p>DNI: <c:out value="${client.dni}"/></p>
                    <p>Teléfono: <c:out value="${client.numberPhone}"/></p>
                    <p>Dirección: <c:out value="${client.address}" /></p>
                    <p>Edad: <c:out value="${client.age}" /> años</p>
                    <p>Sexo: <c:out value="${client.gender.value}" /></p>
                </article>
            </c:forEach>
        </div>
    </div>

    <div class="options-container">
        <header>
            <h2>Reclamos</h2>
            <p><span id="incident-count">Cargando...</span> reclamos</p>
        </header>

        <div class="options-list" id="incident-list">
            <c:forEach var="incident" items="${incidents}">
                <article class="option" data-type="training">
                    <h3><c:out value="${incident.title}"/></h3>
                    <p>Descripción: <c:out value="${incident.description}"/></p>
                    <p>Fecha: <c:out value="${incident.date}"/></p>
                    <p>Cliente: <c:out value="${incident.client.dni}"/></p>
                    <p>Estado: <c:out value="${incident.state.value}"/></p>
                </article>
            </c:forEach>
        </div>
    </div>

    <div class="options-container">
        <header>
            <h2>Membresías</h2>
            <p><span id="memberships-count">Cargando...</span> membresías</p>
        </header>

        <div class="options-list" id="memberships-list">
            <c:forEach var="membership" items="${memberships}">
                <article class="option" data-type="training">
                    <h3><c:out value="${membership.category}"/></h3>
                    <p>Duración: <c:out value="${membership.duration}"/> dias</p>
                    <p>Personalizadas al mes: <c:out value="${membership.sessionPersonalized}"/></p>
                    <p>Grupales al mes: <c:out value="${membership.sessionGroup}"/></p>
                    <p>Precio: S/.<c:out value="${membership.price}"/></p>
                </article>
            </c:forEach>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const membershipCount = document.querySelectorAll('#memberships-list .option').length;
            const incidentCount = document.querySelectorAll('#incident-list .option').length;
            const clientCount = document.querySelectorAll('#client-list .option').length;
            const contractedCount = document.querySelectorAll('#contracted-list .option').length;

            document.getElementById('memberships-count').textContent = membershipCount.toString();
            document.getElementById('incident-count').textContent = incidentCount.toString();
            document.getElementById('clients-count').textContent = (clientCount - 1).toString();
            document.getElementById('contracted-count').textContent = (contractedCount - 1).toString();
        });
    </script>

</body>
</html>
