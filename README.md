# Restaurant Management System

A full-stack restaurant management application built with React, Spring Boot, and PostgreSQL.

## Features

-   Menu management with categories (Appetizers, Main Courses, Side Dishes, Desserts, Beverages)
-   Shopping cart functionality
-   Order processing system
-   Responsive user interface

## Tech Stack

### Frontend

-   React with Vite
-   Tailwind CSS
-   Material-UI Icons
-   REST API integration

### Backend

-   Spring Boot
-   PostgreSQL Database
-   JPA/Hibernate
-   RESTful API

### DevOps

-   Docker
-   Docker Compose

## Getting Started

### Prerequisites

-   Docker and Docker Compose
-   Node.js (for local development)
-   Java 21 (for local development)

### Running with Docker

1. Clone the repository

```bash
git clone <repository-url>
cd restaurant-management
```

2. Start the application using Docker Compose

```bash
cd docker
docker-compose up --build
```

The application will be available at:

-   Frontend: http://localhost:5173
-   Backend: http://localhost:8080
-   Database: http://localhost:5432

### Local Development

#### Frontend

```bash
cd frontend
npm install
npm run dev
```

#### Backend

```bash
cd backend
./mvnw spring-boot:run
```

## API Endpoints

### Menu Items

-   GET `/menu` - Get all menu items
-   POST `/menu` - Add a new menu item
-   PUT `/menu/{id}` - Update a menu item
-   DELETE `/menu/{id}` - Delete a menu item

## Project Structure

```
restaurant-management/
├── frontend/                # React frontend application
│   ├── src/
│   │   ├── components/     # React components
│   │   └── App.jsx        # Main application component
├── backend/                # Spring Boot backend
│   └── src/
│       └── main/
│           ├── java/      # Java source files
│           └── resources/ # Application properties
└── docker/                # Docker configuration files
```
