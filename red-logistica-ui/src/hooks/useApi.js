import { useState, useCallback } from 'react';

export const useApi = () => {
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const request = useCallback(async (url, options = {}) => {
        setLoading(true);
        setError(null);
        setData(null);

        try {
            const response = await fetch(url, options);

            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                const errorMessage = errorData?.message || response.statusText;
                throw new Error(`Error ${response.status}: ${errorMessage}`);
            }

            const result = await response.json();
            setData(result);
            return { success: true, data: result };

        } catch (err) {
            setError(err.message);
            return { success: false, error: err.message };
        } finally {
            setLoading(false);
        }
    }, []); // useCallback para evitar re-creaciones innecesarias

    return { data, error, loading, request };
};
