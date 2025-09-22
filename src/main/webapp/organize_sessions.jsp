<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Fitness | Organizar Sesiones de Entrenamiento</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
            min-height: 100vh;
            color: #1e293b;
            line-height: 1.6;
        }

        /* Header Principal */
        .header-section {
            background: linear-gradient(135deg, #1e293b 0%, #334155 100%);
            color: white;
            padding: 3rem 0;
            margin-bottom: 3rem;
            box-shadow: 0 10px 25px -5px rgba(30, 41, 59, 0.2);
        }

        h1 {
            text-align: center;
            font-size: 2.5rem;
            font-weight: 700;
            letter-spacing: -0.02em;
            margin: 0;
        }

        .subtitle {
            text-align: center;
            font-size: 1.1rem;
            margin-top: 0.5rem;
            opacity: 0.9;
            font-weight: 400;
        }

        /* Contenedores de Sección */
        .complementary-container {
            margin-bottom: 4rem;
            padding: 0 2rem;
            max-width: 1400px;
            margin-left: auto;
            margin-right: auto;
        }

        h2 {
            color: #1e293b;
            font-size: 1.75rem;
            font-weight: 600;
            margin-bottom: 1.5rem;
            padding-left: 1rem;
            border-left: 4px solid #3b82f6;
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        h2::before {
            content: '';
            width: 8px;
            height: 8px;
            background: #3b82f6;
            border-radius: 50%;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
        }

        /* Lista con Scroll Horizontal */
        .complementary-list {
            display: flex;
            gap: 1.5rem;
            overflow-x: auto;
            padding: 1rem 0 1.5rem 0;
            scroll-behavior: smooth;
            -webkit-overflow-scrolling: touch;
        }

        .complementary-list::-webkit-scrollbar {
            height: 8px;
            margin-top: 10px;
        }

        .complementary-list::-webkit-scrollbar-track {
            background: #f1f5f9;
            border-radius: 10px;
            margin: 0 1rem;
        }

        .complementary-list::-webkit-scrollbar-thumb {
            background: linear-gradient(90deg, #3b82f6, #1d4ed8);
            border-radius: 10px;
            border: 2px solid #f1f5f9;
        }

        .complementary-list::-webkit-scrollbar-thumb:hover {
            background: linear-gradient(90deg, #1d4ed8, #1e40af);
        }

        /* Tarjetas Empresariales */
        .complementary-card {
            flex: 0 0 auto;
            width: 340px;
            background: white;
            border-radius: 12px;
            box-shadow:
                    0 4px 6px -1px rgba(0, 0, 0, 0.1),
                    0 2px 4px -1px rgba(0, 0, 0, 0.06);
            border: 1px solid #e2e8f0;
            transition: all 0.3s ease;
            overflow: hidden;
            position: relative;
            padding: 10px;
        }

        .complementary-card:hover {
            transform: translateY(-4px);
            box-shadow:
                    0 20px 25px -5px rgba(0, 0, 0, 0.1),
                    0 10px 10px -5px rgba(0, 0, 0, 0.04);
            border-color: #3b82f6;
        }

        .complementary-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #3b82f6, #8b5cf6, #ec4899);
            border-radius: 12px 12px 0 0;
        }

        .card-header {
            padding: 1.5rem 1.5rem 0 1.5rem;
            border-bottom: 1px solid #f1f5f9;
            margin-bottom: 1rem;
        }

        .card-title {
            font-size: 1.25rem;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 0.5rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .card-title::before {
            content: '📍';
            font-size: 1rem;
        }

        .complementary-card[data-type="training"] .card-title::before {
            content: '💪';
        }

        .card-body {
            padding: 0 1.5rem 1.5rem 1.5rem;
        }

        .complementary-card p {
            margin: 0 0 0.75rem 0;
            font-size: 0.95rem;
            color: #64748b;
            font-weight: 500;
            display: flex;
            align-items: flex-start;
            gap: 0.75rem;
            padding: 0.5rem 0;
            border-bottom: 1px solid rgba(241, 245, 249, 0.8);
        }

        .complementary-card p:last-child {
            border-bottom: none;
            margin-bottom: 0;
        }

        .complementary-card p strong {
            color: #374151;
            font-weight: 600;
            min-width: 80px;
            flex-shrink: 0;
            font-size: 0.875rem;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        .complementary-card p span {
            color: #1e293b;
            font-weight: 500;
        }

        /* Estados Activo/Inactivo */
        .status-badge {
            display: inline-flex;
            align-items: center;
            gap: 0.375rem;
            padding: 0.375rem 0.75rem;
            border-radius: 6px;
            font-size: 0.75rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        .status-active {
            background: #dcfce7;
            color: #166534;
            border: 1px solid #bbf7d0;
        }

        .status-active::before {
            content: '●';
            color: #22c55e;
            font-size: 0.5rem;
        }

        .status-inactive {
            background: #fef2f2;
            color: #991b1b;
            border: 1px solid #fecaca;
        }

        .status-inactive::before {
            content: '●';
            color: #ef4444;
            font-size: 0.5rem;
        }

        /* Navegación de Scroll */
        .scroll-container {
            position: relative;
        }

        .scroll-navigation {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
        }

        .scroll-btn {
            background: white;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            padding: 0.5rem;
            cursor: pointer;
            transition: all 0.2s ease;
            color: #64748b;
            font-size: 1.25rem;
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .scroll-btn:hover {
            background: #f8fafc;
            border-color: #3b82f6;
            color: #3b82f6;
        }

        .scroll-btn:disabled {
            opacity: 0.4;
            cursor: not-allowed;
        }

        .list-counter {
            font-size: 0.875rem;
            color: #64748b;
            font-weight: 500;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .complementary-container {
                padding: 0 1rem;
            }

            .complementary-card {
                width: 300px;
            }

            h1 {
                font-size: 2rem;
            }

            h2 {
                font-size: 1.5rem;
            }

            .header-section {
                padding: 2rem 0;
            }
        }

        @media (max-width: 480px) {
            .complementary-card {
                width: 280px;
            }

            .scroll-navigation {
                display: none;
            }
        }

        /* Indicadores de scroll */
        .scroll-indicator {
            display: flex;
            justify-content: center;
            gap: 0.5rem;
            margin-top: 1rem;
        }

        .scroll-dot {
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background: #cbd5e1;
            transition: background 0.3s ease;
        }

        .scroll-dot.active {
            background: #3b82f6;
        }

        /* Animaciones de entrada */
        @keyframes slideInFromRight {
            from {
                opacity: 0;
                transform: translateX(50px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }

        .complementary-card {
            animation: slideInFromRight 0.5s ease forwards;
        }

        .complementary-card:nth-child(1) { animation-delay: 0.1s; }
        .complementary-card:nth-child(2) { animation-delay: 0.2s; }
        .complementary-card:nth-child(3) { animation-delay: 0.3s; }
        .complementary-card:nth-child(4) { animation-delay: 0.4s; }
        .complementary-card:nth-child(5) { animation-delay: 0.5s; }
    </style>
</head>
<body>
    <div class="header-section">
        <h1>Panel de Control Fitness</h1>
        <p class="subtitle">Gestión Integral de Sesiones de Entrenamiento</p>
    </div>

    <div class="complementary-container">
        <div class="scroll-navigation">
            <h2>Instalaciones</h2>
            <div class="list-counter">
                <span id="sites-count">Cargando...</span> ubicaciones disponibles
            </div>
        </div>

        <div class="scroll-container">
            <div class="complementary-list" id="sites-list">
                <c:forEach var="site" items="${sites}">
                    <div class="complementary-card">
                        <div class="card-header">
                            <div class="card-title"><c:out value="${site.name}"/></div>
                        </div>
                        <div class="card-body">
                            <p><strong>Dirección:</strong> <span><c:out value="${site.address}"/></span></p>
                            <p><strong>Tipo:</strong> <span><c:out value="${site.type.getValue()}"/></span></p>
                            <p><strong>Capacidad:</strong> <span><c:out value="${site.capacity}"/> personas</span></p>
                            <p><strong>Horario:</strong> <span><c:out value="${site.opening}"/> - <c:out value="${site.closing}"/></span></p>
                            <p><strong>Estado:</strong>
                                <span class="status-badge ${site.state ? 'status-active' : 'status-inactive'}">
                                        <c:out value="${site.state ? 'Activo' : 'Inactivo'}"/>
                                    </span>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <div class="complementary-container">
        <div class="scroll-navigation">
            <h2>Entrenamiento</h2>
            <div class="list-counter">
                <span id="trainings-count">Cargando...</span> entrenamientos activos
            </div>
        </div>

        <div class="scroll-container">
            <div class="complementary-list" id="trainings-list">
                <c:forEach var="training" items="${trainings}">
                    <div class="complementary-card" data-type="training">
                        <div class="card-header">
                            <div class="card-title"><c:out value="${training.name}"/></div>
                        </div>
                        <div class="card-body">
                            <p><strong>Descripción:</strong> <span><c:out value="${training.description}"/></span></p>
                            <p><strong>Duración:</strong> <span><c:out value="${training.duration}"/> minutos</span></p>
                            <p><strong>Capacidad:</strong> <span><c:out value="${training.maxParticipants}"/> participantes</span></p>
                            <p><strong>Estado:</strong>
                                <span class="status-badge ${training.state ? 'status-active' : 'status-inactive'}">
                                        <c:out value="${training.state ? 'Activo' : 'Inactivo'}"/>
                                    </span>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const sitesCount = document.querySelectorAll('#sites-list .complementary-card').length;
            const trainingsCount = document.querySelectorAll('#trainings-list .complementary-card').length;

            document.getElementById('sites-count').textContent = sitesCount.toString();
            document.getElementById('trainings-count').textContent = trainingsCount.toString();
        });
    </script>
</body>
</html>