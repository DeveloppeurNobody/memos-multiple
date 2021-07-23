package coroutines

/*


suspend fun CameraManager.openCamera(cameraId: String): CameraDevice? =
   suspendCoroutine { cont ->

        val callback = object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                cont.resume(camera)
            }

            override fun onDisconnected(camera: CameraDevice) {
                cont.resume(null)
            }

            override fun onError(camera: CameraDevice, error: Int) {
                // assuming that we don't care about the error in this example
                cont.resume(null)
            }
        }
        openCamera(cameraId, callback, null)
    }

 */