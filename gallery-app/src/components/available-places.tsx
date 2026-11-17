import Places from "./places";
import { sortPlacesByDistance } from "../loc";
import type { TAvailablePlace } from "@/types/gallery.type";
import { fetchAvailablePlaces } from "../http";
import { useGetFetch } from "../hook/use-fetch";

type Props = {
  onSelect: (selectedPlace: TAvailablePlace) => void;
}

async function fetchSortedPlaces(): Promise<TAvailablePlace[]> {
  const places = await fetchAvailablePlaces();
  return new Promise((resolve) => {
    navigator.geolocation.getCurrentPosition((position) => {
      const sortedPlaces = sortPlacesByDistance(
        places,
        position.coords.latitude,
        position.coords.longitude
      );
      resolve(sortedPlaces);
    });
  });
}

export default function AvailablePlaces({ onSelect }: Props) {
  const { isFetching, error, fetchedData: availablePlaces } = useGetFetch(fetchSortedPlaces, []);
  if (error && error.message) {
    return (
      <div className="mx-auto flex w-[350px] rounded-md items-center justify-center p-4 mt-12 mb-16 bg-red-500">
        <h1 className="text-center font-semibold text-white text-lg md:text-xl">
          {error.message}
        </h1>
      </div>
    );
  }

  return (
    <Places
      isLoading={isFetching}
      title="Available Places"
      places={availablePlaces}
      onSelectPlace={onSelect}
    />

  )
}