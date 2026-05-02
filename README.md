# 🚀 Fullstack Authentication System

A full-stack authentication system built with **Spring Boot**, **Spring Security**, **JWT**, and **Next.js**.  
This project demonstrates secure login, role-based access control, and token-based authentication with refresh token support.

---

## 🌐 Live Demo

- 🔗 Frontend: https://your-frontend.vercel.app
- 🔗 Backend API: https://logindemo-production.up.railway.app

---

## 🧠 Features

- 🔐 User Registration & Login
- 🔑 JWT Authentication
- 🔄 Refresh Token (Auto Login)
- 🛡️ Role-Based Access Control (RBAC)
- ⚙️ Spring Security Integration
- 🌐 RESTful API
- ⚡ Next.js Frontend
- ☁️ Cloud Deployment (Railway + Vercel)

---

## 🏗️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Hibernate / JPA
- MySQL
- JWT (JSON Web Token)

### Frontend
- Next.js
- React
- Tailwind CSS

### Deployment
- Backend: Railway
- Frontend: Vercel
- Database: Railway MySQL

---

## 🔐 Authentication Flow

1. User logs in with email & password
2. Server returns:
   - Access Token (short-lived)
   - Refresh Token (long-lived)
3. Frontend stores tokens in localStorage
4. API requests include:



Authorization: Bearer <accessToken>

5. If access token expires:
- Automatically request new token using refresh token

---

## 🧪 API Endpoints

### Auth

| Method | Endpoint              | Description        |
|--------|----------------------|--------------------|
| POST   | /api/auth/register   | Register new user |
| POST   | /api/auth/login      | Login             |
| POST   | /api/auth/refresh    | Refresh token     |

---

### Protected Routes

| Endpoint       | Role Required |
|---------------|--------------|
| /api/test      | Authenticated |
| /api/user      | USER         |
| /api/admin     | ADMIN        |

---

## 📦 Project Structure




backend/
├── controller/
├── service/
├── entity/
├── repository/
├── security/
frontend/
├── app/
├── components/
├── lib/


---

## ⚙️ Setup Instructions

### Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
