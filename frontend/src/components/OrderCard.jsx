import React from "react";
import DeleteIcon from "@mui/icons-material/Delete";

function OrderCard({ orderItems, status, id, totalPrice, onDelete }) {
    if (!Array.isArray(orderItems)) {
        return <p>Invalid order data</p>;
    }

    const handleDelete = async () => {
        const url = "http://localhost:8080/api/order/" + id;
        try {
            await fetch(url, {
                method: "DELETE",
            });
            if (onDelete) {
                onDelete();
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <div className="bg-lime-50 rounded-2xl p-4 mb-4 flex flex-col gap-2">
            <div className="flex flex-col gap-2">
                {orderItems.map((item) => (
                    <div key={item.id} className="flex justify-between">
                        <span>{item.menuItem.name}</span>
                        <span>x{item.quantity}</span>
                    </div>
                ))}
            </div>
            <div className="flex justify-between">
                {status && (
                    <div className="mt-2 text-sm text-gray-500">
                        Status: {status}
                    </div>
                )}
                <button onClick={handleDelete}>
                    <DeleteIcon
                        sx={{ color: "#4a5565", "&:hover": { color: "red" } }}
                    />
                </button>
            </div>

            <h2 className="text-xl text-gray-600">${totalPrice}</h2>
        </div>
    );
}

export default OrderCard;
