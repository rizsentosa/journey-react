import { useEffect, useState } from "react";

export function useGetFetch<T, P>(
  fetchFn: (param: P | undefined) => Promise<T>,
  initialValue: T,
  param?: P
) {
  const [isFetching, setIsFetching] = useState<boolean>(false);
  const [error, setError] = useState<{ message: string } | null>(null);
  const [fetchedData, setFetchedData] = useState<T>(initialValue);

  useEffect(() => {
    async function fetchData() {
      setIsFetching(true);
      try {
        const fetchData = param ? await fetchFn(param) : await fetchFn(undefined);
        setFetchedData(fetchData);
      } catch (error) {
        if (error instanceof Error) {
          setError({ message: error.message });
        }
      }
      setIsFetching(false);
    }
    fetchData();
  }, [fetchFn, param]);

  return {
    isFetching, error, fetchedData, setFetchedData
  }
}
