import { describe, expect, it, test } from "vitest";
import { render, screen } from "@testing-library/react";
import OrderCard from "./OrderCard";

test("renders order status", () => {
    render(
        <OrderCard
            orderItems={[{ id: 1, menuItem: { name: "Pizza" }, quantity: 2 }]}
            status="PENDING"
            id={1}
            totalPrice={20}
            onDelete={() => {}}
        />
    );
    expect(screen.getByText(/Status: PENDING/i)).toBeInTheDocument();
    expect(screen.getByText(/Pizza/)).toBeInTheDocument();
});
