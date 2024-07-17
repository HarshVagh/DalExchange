import React from "react";
import { Link, useLocation } from "react-router-dom";
import StoreIcon from "../assets/icons/store-solid.svg";

function Sidebar() {
  const location = useLocation();

  return (
    <aside className="fixed inset-y-0 left-0 flex flex-col w-64 bg-gray-900 text-white p-4">
      <div className="flex items-center mb-8">
        <img src={StoreIcon} alt="Store Icon" className="h-8 w-8 mr-4" />
        <span className="ml-4 text-lg font-bold">Dal Exchange</span>
      </div>
      <nav className="flex flex-col space-y-4">
        <Link to="/admin">
          <button
            className={`w-full py-2 px-4 text-center rounded ${
              location.pathname === "/admin"
                ? "bg-blue-700"
                : "bg-blue-500 hover:bg-blue-600 transition"
            }`}
          >
            Dashboard
          </button>
        </Link>
        <Link to="/admin-moderation/orders">
          <button
            className={`w-full py-2 px-4 text-center rounded ${
              location.pathname === "/admin-moderation/orders"
                ? "bg-blue-700"
                : "bg-blue-500 hover:bg-blue-600 transition"
            }`}
          >
            Orders
          </button>
        </Link>
        <Link to="/admin-moderation/users">
          <button
            className={`w-full py-2 px-4 text-center rounded ${
              location.pathname === "/admin-moderation/users"
                ? "bg-blue-700"
                : "bg-blue-500 hover:bg-blue-600 transition"
            }`}
          >
            Users
          </button>
        </Link>
        <Link to="/admin-moderation/products">
          <button
            className={`w-full py-2 px-4 text-center rounded ${
              location.pathname === "/admin-moderation/products"
                ? "bg-blue-700"
                : "bg-blue-500 hover:bg-blue-600 transition"
            }`}
          >
            Products
          </button>
        </Link>
        <Link to="/admin/feedbacks">
          <button
            className={`w-full py-2 px-4 text-center rounded ${
              location.pathname === "/admin/feedbacks"
                ? "bg-blue-700"
                : "bg-blue-500 hover:bg-blue-600 transition"
            }`}
          >
            Feedbacks
          </button>
        </Link>
      </nav>
      <div className="mt-auto">
        <Link to="/logout">
          <button className="w-full py-2 px-4 bg-red-600 text-center text-white rounded hover:bg-red-700 transition">
            Logout
          </button>
        </Link>
      </div>
    </aside>
  );
}

export default Sidebar;
