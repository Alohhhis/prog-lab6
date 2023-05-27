package utils

import data.FuelType
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
    fun getFuelType(): FuelType
    /**
     * Принимает [VehicleType] значение
     */
    fun getVehicleType(): VehicleType
}
/*
INT,
STRING,
FUELTYPE,
VEHICLE_TYPE
 */