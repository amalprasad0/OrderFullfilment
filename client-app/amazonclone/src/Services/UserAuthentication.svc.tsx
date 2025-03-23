import axios from "axios";

export type userRegisterData={
    

    email: string;
    password: string;
    firstName: string;
    lastName: string;
    phone: string;
    username: string;
}

export const registerUser = async (userRegisterData: userRegisterData) => {
  try {
    const response = await axios.post('http://localhost:8080/userauth/registerUser', userRegisterData);
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw new Error(error.response?.data?.message || 'Registration failed');
    } else {
      throw new Error('An unexpected error occurred');
    }
  }
};
