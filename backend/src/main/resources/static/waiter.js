async function updateOrderStatus(orderId) {
    const url = `http://localhost:8080/api/order/${orderId}`;
    const newStatus = "DELIVERED";

    try {
        const response = await fetch(url, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                orderStatus: newStatus,
            }),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        window.location.reload();
    } catch (error) {
        console.error("Error updating order status:", error);
        alert("Failed to update order status");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const fetchData = async () => {
        const url = "http://localhost:8080/api/order";
        const response = await fetch(url);
        if (!response.ok)
            throw new Error(`HTTP error! Status: ${response.status}`);

        const jsonData = await response.json();
        console.log(jsonData);

        const listGroup = document.querySelector("ul");

        jsonData.forEach((order) => {
            const orderItem = document.createElement("li");
            orderItem.classList.add("order-item");

            orderItem.innerHTML = `
                <h3>Total: $${order.totalPrice.toFixed(2)}</h3>
                <div class="order-item-flex">
                    <p>Order #${order.id}</p>
                    <p>Table: ${order.tableId}</p>
                    <p>Status: ${order.orderStatus}</p>
                    <p>Date: ${new Date(
                        order.localDateTime
                    ).toLocaleString()}</p>
                </div>
                <h4>Items:</h4>
                <ul class="order-items-list">
                    ${order.orderItems
                        .map(
                            (item) => `
                        <li>
                            ${item.quantity} x ${item.menuItem.name} - $${(
                                item.menuItem.price * item.quantity
                            ).toFixed(2)}
                        </li>
                    `
                        )
                        .join("")}
                </ul>

                <button onClick="updateOrderStatus('${
                    order.id
                }')">Set delivered</button>
            `;

            listGroup.appendChild(orderItem);
        });
    };

    fetchData();
});
