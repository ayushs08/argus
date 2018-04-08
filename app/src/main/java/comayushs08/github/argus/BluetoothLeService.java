package comayushs08.github.argus;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.List;

public class BluetoothLeService extends Service {
    private BluetoothGatt bluetoothGatt;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private String deviceAddress;
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        bluetoothGatt.close();
        return super.onUnbind(intent);
    }

    /**
     * Initializes the reference to the BluetoothAdapter.
     * @return true if initialization was successful.
     */
    public boolean initialize() {
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null || bluetoothManager == null) { return false; }
        return true;
    }

    /**
     * Connect to the GATT server hosted on the BLE device.
     * @param address The device address of the BLE device.
     * @return true if the connection was successfully initiated.
     */
    public boolean connect(String address) {
        // Attempt re-connection
        if (bluetoothGatt != null && address.equals(deviceAddress)) {
            return bluetoothGatt.connect();
        }
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        if (device == null) { return false; }
        bluetoothGatt = device.connectGatt(this, false, gattCallback);
        deviceAddress = address;
        return true;
    }

    /**
     * Disconnects an established connection, or cancels a connection attempt currently in progress.
     */
    public void disconnect() {
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
        }
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    bluetoothGatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // remote device has been explored successfully
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }
    };

    /**
     * Returns a list of GATT services offered by the remote device.
     * This function requires that service discovery has been completed for the given device.
     * @return List of services on the remote device. Returns an empty list if service discovery has not yet been performed.
     */
    public List<BluetoothGattService> getSupportedGattServices () {
        if (bluetoothGatt == null) { return null; }
        return bluetoothGatt.getServices();
    }
}