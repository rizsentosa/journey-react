import { useCallback, useEffect, useRef, useState } from "react"
import { AVAILABLE_PLACES } from "./data";
import type { TAvailablePlace } from "@/types/gallery.type";
import logoImg from "./assets/logo.png";
import Modal from "./components/modal";
import DeleteConfirmation from "./components/delete-confirmation";
import Places from "./components/places";
import { sortPlacesByDistance } from "./loc";


const storeData = localStorage.getItem("places");
const storeIds: string[] = storeData ? JSON.parse(storeData) : [];
const storePlaces = storeIds.map(id => AVAILABLE_PLACES.find(p => p.id === id)!);

function App() {
  const selectedPlace = useRef<string>(null);
  const [availablePlaces, setAvailablePlaces] = useState<TAvailablePlace[]>([]);
  const [pickedPlaces, setPickedPlaces] = useState<TAvailablePlace[]>(storePlaces);
  const [open, setOpen] = useState<boolean>(false);

  useEffect(() => {
    navigator.geolocation.getCurrentPosition((position) => {
      const sortedPlaces = sortPlacesByDistance(
        AVAILABLE_PLACES,
        position.coords.latitude,
        position.coords.longitude
      );
      setAvailablePlaces(sortedPlaces);
    });
  }, []);

  const handleStartRemovePlace = (id: string) => {
    setOpen(true);
    selectedPlace.current = id;
  }

  const handeStopRemovePlace = () => { setOpen(false) }

  const handleSelectPlace = (id: string) => {
    setPickedPlaces((prevPickedPlaces) => {
      if (prevPickedPlaces.some(place => place.id === id)) {
        return prevPickedPlaces;
      }
      const place = AVAILABLE_PLACES.find(place => place.id === id)!;
      return [place, ...prevPickedPlaces];
    });

    const storeData = localStorage.getItem("places");
    const storeIds = storeData ? JSON.parse(storeData) : [];
    if (storeData?.indexOf(id) === -1) {
      localStorage.setItem("places", JSON.stringify([id, ...storeIds]));
    }
  }

  const handleRemovePlace = useCallback(() => {
    console.log("remove place");
    if (open) {
      setPickedPlaces(prevPickedPlaces => prevPickedPlaces.filter(place => place.id !== selectedPlace.current));
      localStorage.setItem("places", JSON.stringify(pickedPlaces.filter(place => place.id !== selectedPlace.current).map(place => place.id)));
      setOpen(false);
    }
  }, [pickedPlaces, open]);

  return (
    <div className="bg-slate-900">
      <Modal open={open} onClose={handleRemovePlace}>
        <DeleteConfirmation onConfirm={handleRemovePlace} onCancel={handeStopRemovePlace} />
      </Modal>
      <header className="flex flex-col items-center justify-center text-center py-8 px-4 max-w-2xl mx-auto">
        {/* Logo dengan Efek Shadow & Ukuran Responsif */}
        <img
          src={logoImg}
          alt="Stylized globe"
          className="h-20 w-20 md:h-24 md:w-24 object-contain mb-4 drop-shadow-md transition-transform duration-300 hover:scale-105"
        />

        {/* Judul Utama */}
        <h1 className="text-3xl md:text-5xl font-extrabold tracking-widest uppercase text-gray-900 mb-3 bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600 bg-clip-text text-transparent">
          PlacePicker
        </h1>

        {/* Deskripsi Sub-header */}
        <p className="text-base md:text-lg text-gray-600 font-normal leading-relaxed max-w-lg">
          Create your personal collection of places you would like to visit or
          you have visited.
        </p>
      </header>
      <Places
        title="I'd like to visit ..."
        fallbackText={"Select the places you would like to visit below."}
        places={pickedPlaces}
        onSelectPlace={handleStartRemovePlace}
      />
      <Places
        title="Available Places"
        places={availablePlaces}
        onSelectPlace={handleSelectPlace}
      />
    </div>
  )
}

export default App
