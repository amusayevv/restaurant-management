import { describe, expect, it, test } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import MenuCard from "./MenuCard";

test("renders menu item details", () => {
    const mockAddToCart = vi.fn();
    render(
        <MenuCard
            id={1}
            name="Burger"
            description="Delicious burger"
            category="MAIN_COURSE"
            price={10}
            addToCart={mockAddToCart}
        />
    );

    expect(screen.getByText("Burger")).toBeInTheDocument();
    expect(screen.getByText("Delicious burger")).toBeInTheDocument();
    expect(screen.getByText("Main Course")).toBeInTheDocument();
    expect(screen.getByText("Add to cart $10")).toBeInTheDocument();
});

test("calls addToCart when button is clicked", () => {
    const mockAddToCart = vi.fn();
    render(
        <MenuCard
            id={1}
            name="Burger"
            description="Delicious burger"
            category="MAIN_COURSE"
            price={10}
            addToCart={mockAddToCart}
        />
    );

    fireEvent.click(screen.getByText("Add to cart $10"));
    expect(mockAddToCart).toHaveBeenCalledTimes(1);
});
