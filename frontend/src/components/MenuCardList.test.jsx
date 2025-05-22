import { describe, expect, it, test } from "vitest";
import { render, screen, waitFor } from "@testing-library/react";
import MenuCardList from "./MenuCardList";

global.fetch = vi.fn();

beforeEach(() => {
    fetch.mockReset();
});

test("fetches and renders menu items", async () => {
    const mockMenuItems = [
        {
            id: 1,
            name: "Pizza",
            description: "Delicious pizza",
            category: "MAIN_COURSE",
            price: 15,
        },
    ];

    fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockMenuItems,
    });

    render(<MenuCardList addToCart={() => {}} />);

    await waitFor(() => {
        expect(screen.getByText("Pizza")).toBeInTheDocument();
        expect(screen.getByText("Delicious pizza")).toBeInTheDocument();
    });
});
