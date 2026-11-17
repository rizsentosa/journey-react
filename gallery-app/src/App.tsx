import { useCallback, useRef, useState } from "react"
import type { TAvailablePlace } from "@/types/gallery.type";
import logoImg from "./assets/logo.png";
import Modal from "./components/modal";
import DeleteConfirmation from "./components/delete-confirmation";
import Places from "./components/places";
import AvailablePlaces from "./components/available-places";
import { fetchDeleteUserPlaces, fetchUserPlaces, updateUserPlaces } from "./http";
import ErrorServer from "./components/error";
import { useGetFetch } from "./hook/use-fetch";


function App() {
  const selectedPlace = useRef<string>(null);
  const [open, setOpen] = useState<boolean>(false);
  const [errorUpdatingPlaces, setErrorUpdatingPlaces] = useState<{ message: string } | null>(null);
  const { isFetching, error, fetchedData: pickedPlaces,
    setFetchedData: setPickedPlaces } = useGetFetch(() => fetchUserPlaces(), []);

  const handleStartRemovePlace = (place: TAvailablePlace) => {
    setOpen(true);
    selectedPlace.current = place.id;
  }

  const handeStopRemovePlace = () => { setOpen(false) }

  const handleSelectPlace = async (selectedPlace: TAvailablePlace) => {
    try {
      setPickedPlaces((prevPickedPlaces) => {
        if (!prevPickedPlaces) {
          prevPickedPlaces = [];
        }
        if (prevPickedPlaces.some(place => place.id === selectedPlace.id)) {
          return prevPickedPlaces;
        }
        return [selectedPlace, ...prevPickedPlaces];
      });
      await updateUserPlaces([selectedPlace, ...pickedPlaces]);
    } catch (error) {
      if (error instanceof Error) {
        setPickedPlaces(pickedPlaces);
        setErrorUpdatingPlaces({ message: error.message });
      }
    }

  }

  const handleRemovePlace = useCallback(async () => {
    if (open) {
      if (selectedPlace.current !== null) {
        setPickedPlaces(prevPickedPlaces => prevPickedPlaces.filter(place => place.id !== selectedPlace.current));
        try {
          await fetchDeleteUserPlaces(selectedPlace.current!);
        } catch (error) {
          console.log(String(error));
        }
      }
      setOpen(false);
    }
  }, [open, setPickedPlaces]);

  const handleErrorUpdated = () => {
    setErrorUpdatingPlaces(null);
  }


  return (
    <div className="bg-slate-900">
      <Modal open={errorUpdatingPlaces !== null} onClose={handleErrorUpdated}>
        {errorUpdatingPlaces && (
          <ErrorServer title="An error occured" message={errorUpdatingPlaces.message} onConfirm={handleErrorUpdated} />
        )}
      </Modal>
      <Modal open={open} onClose={handleRemovePlace}>
        <DeleteConfirmation onConfirm={handleRemovePlace} onCancel={handeStopRemovePlace} />
      </Modal>
      <header className="flex flex-col items-center justify-center text-center py-8 px-4 max-w-2xl mx-auto">
        <img
          src={logoImg}
          alt="Stylized globe"
          className="h-20 w-20 md:h-24 md:w-24 object-contain mb-4 drop-shadow-md transition-transform duration-300 hover:scale-105"
        />

        <h1 className="text-3xl md:text-5xl font-extrabold tracking-widest uppercase text-gray-900 mb-3 bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600 bg-clip-text text-transparent">
          PlacePicker
        </h1>

        <p className="text-base md:text-lg text-gray-600 font-normal leading-relaxed max-w-lg">
          Create your personal collection of places you would like to visit or
          you have visited.
        </p>
      </header>
      {error && <ErrorServer title="An error occured" message={error.message} />}
      {!error && <Places
        isLoading={isFetching}
        title="I'd like to visit ..."
        fallbackText={"Select the places you would like to visit below."}
        places={pickedPlaces}
        onSelectPlace={handleStartRemovePlace}
      />}
      <AvailablePlaces onSelect={handleSelectPlace} />
    </div>
  )
}

export default App;
