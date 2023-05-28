package utils

import data.FuelType
import data.Vehicle
import data.VehicleType

interface Validator {
    /**
     * Принимает [String] значение
     */
    fun getString(): String

    /**
     * Принимает [Int] значение
     */
    fun getInt(): Int
    /**
     * Принимает [FuelType] значение
     */
    fun getVehicleType(): VehicleType
    /**
     * Принимает [VehicleType] значение
     */
    fun getVehicle(): Vehicle
}
/*
INT,
STRING,
FUELTYPE,
VEHICLE_TYPE
 */