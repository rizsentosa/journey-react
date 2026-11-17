import type { TAvailablePlace } from "./types/gallery.type";

export async function fetchAvailablePlaces(): Promise<TAvailablePlace[]> {
  const response = await fetch("http://localhost:8080/api/v1/public/places", {
    headers: {
      "Content-Type": "application/json",
      "My-Token": "qwerty123@"
    }
  });
  if (!response.ok) {
    throw new Error("failed to fetch places");
  }

  const data = await response.json();
  return data;
}

export async function updateUserPlaces(places: TAvailablePlace[]) {
  const response = await fetch('http://localhost:8080/api/v1/public/places/user-places', {
    method: "put",
    body: JSON.stringify({ places }),
    headers: {
      "Content-Type": "application/json",
      "My-Token": "qwerty123@"
    }
  });
  if (!response.ok) {
    throw new Error("failed to updaate user data.")
  }
  return response.json();
}

export async function fetchUserPlaces(): Promise<TAvailablePlace[]> {
  const response = await fetch('http://localhost:8080/api/v1/public/places/user-places', {
    headers: {
      "Content-Type": "application/json",
      "My-Token": "qwerty123@"
    }
  })

  if (!response.ok) {
    throw new Error("failed to fetch user data or backend not ready hehe");
  }

  return response.json();
}

export async function fetchDeleteUserPlaces(id: string) {
  const response = await fetch('http://localhost:8080/api/v1/public/places/user-places/' + id, {
    method: "delete",
    headers: {
      "Content-Type": "application/json",
      "My-Token": "qwerty123@"
    }
  })
  if (!response.ok) {
    throw new Error("failed to fetch user data or backend not ready hehe");
  }
  return response.json();
}