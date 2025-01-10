# eFood App 🍴  
An online food ordering and tracking application for a single restaurant. This project is designed to provide customers with a seamless experience for browsing menus, placing orders, and tracking them in real-time. The admin dashboard enables efficient order management.

## Features  
### Customer Features:  
- **User Account Management**: Create and log in to accounts.  
- **Browse Menu**: View the restaurant’s menu with detailed food descriptions and prices.  
- **Order Food**: Select items, add to cart, and place orders.  
- **Order Tracking**: Real-time updates on the status of your order (e.g., Accepted, Cooking, Ready, Delivered).  

### Admin Features:  
- **Dashboard Management**: View all incoming orders.  
- **Order Updates**: Change the status of orders (Accept, Cooking, Ready, Delivered).  

## Tech Stack  
### Backend:  
- **Java Spring Boot**: REST API development for user, food, and order management.  
- **WebSocket**: Real-time order status updates.  
- **PostgreSQL**: Database for storing user, food, and order data.  

### Frontend:  
- **React**: User-friendly interface for both customers and admins.  

### Deployment:  
- **Render**: Hosting the server and database.  

## Database Schema  
The database consists of four tables: `user_table`, `orders`, `order_item`, and `food`.

### `user_table`  
Stores information about users, including their roles and login credentials.  
- **Columns**:  
  - `id`: Primary key.  
  - `name`: Name of the user.  
  - `phoneNumber`: User's phone number (unique).  
  - `password`: Securely hashed password.  
  - `role`: Role of the user (e.g., `customer`, `admin`).  
  - `logoUrl`: URL of the user's profile image/logo.  
  - `created_at`: Timestamp of when the user was created.  

### `food`  
Stores details about the food items.  
- **Columns**:  
  - `id`: Primary key.  
  - `name`: Name of the food item.  
  - `description`: Detailed description.  
  - `imageUrl`: URL for the food image.  
  - `price`: Price of the food item.  
  - `isAvailable`: Boolean indicating availability.  
  - `quantity`: Quantity of the item available.  

### `orders`  
Stores details about customer orders.  
- **Columns**:  
  - `order_id`: Primary key.  
  - `userId`: Foreign key referencing `user_table(id)`.  
  - `status`: Status of the order (e.g., `Pending`, `Cooking`, `Delivered`).  
  - `createdAt`: Timestamp of when the order was created.  

### `order_item`  
Stores the items included in an order.  
- **Columns**:  
  - `id`: Primary key.  
  - `order_id`: Foreign key referencing `orders(order_id)`.  
  - `foodId`: Foreign key referencing `food(id)`.  
  - `quantity`: Number of units of the food item in the order.  

### Relationships  
- Each `user_table` entry can place multiple `orders`.  
- Each `order` can have multiple `order_item` entries.  
- Each `order_item` references one `food` item.

### Visual Representation  
Here’s the database schema diagram:  
![image](https://github.com/user-attachments/assets/58747a17-6683-4aa8-bff4-c20dde2b7823)


## Installation  
1. Clone the repository:  
   ```bash  
   git clone https://github.com/your-username/efood-app.git  
   cd efood-app  
