package it.digitalnucleus.lwjgl.VulkanTest;

import org.lwjgl.vulkan.*;
import org.lwjgl.vulkan.VkQueueFamilyProperties.Buffer;
import org.lwjgl.glfw.*;

import static org.lwjgl.vulkan.VK10.*;

import java.nio.IntBuffer;

import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.PointerBuffer;

public class Renderer {
	
	private VkInstance instance;
	private VkDevice   device;
	
	public Renderer() {
		_InitInstance();
	}
	
	public void shutDown() {
		_DeInitInstance();
	}
	
	// Instance creation
	private void _InitInstance() {
		
		VkApplicationInfo pApplicationInfo = VkApplicationInfo.create()
				.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
				.pApplicationName(memUTF8("Vulkan Demo 1"))
				.applicationVersion(1)
				.apiVersion(VK_MAKE_VERSION(1, 0, 61));
		
		VkInstanceCreateInfo pCreateInfo = VkInstanceCreateInfo.create()
				.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
				.pApplicationInfo(pApplicationInfo);
		
		PointerBuffer _instance = memAllocPointer(1);
		int err = vkCreateInstance(pCreateInfo, null, _instance);
		if (VK_SUCCESS != err) {
			throw new AssertionError("Error in initializing Vulkan Instance.");
		}
		
		long inst_handle = _instance.get(0);
		instance = new VkInstance(inst_handle, pCreateInfo);
		System.out.println("Instance initialized correctly. Handle: " + instance);
		// Freeing no longer needed resources.
		pApplicationInfo.free();
		_instance.free();
		pCreateInfo.free();
	}
	
	private void _DeInitInstance() {
		vkDestroyInstance(instance, null);
		instance = null;
	}
	
	// Logical device creation
	private void _InitDevice() {
		
		
		IntBuffer pPhysicalDeviceCount = memAllocInt(1);
		int err = vkEnumeratePhysicalDevices(instance, pPhysicalDeviceCount, null);
		if (VK_SUCCESS != err) {
			throw new AssertionError("Failed to enumerate Vulkan devices");
		}
		
		PointerBuffer pPhysicalDevices = memAllocPointer(pPhysicalDeviceCount.get(0));
		err = vkEnumeratePhysicalDevices(instance, pPhysicalDeviceCount, pPhysicalDevices);
		if (VK_SUCCESS != err) {
			throw new AssertionError("Failed to get Vulkan devices specs.");
		}
		
		VkPhysicalDevice[] physicalDevices = new VkPhysicalDevice[pPhysicalDeviceCount.remaining()];
		for (int i = 0; i < pPhysicalDeviceCount.remaining(); i++) {
			long physical_device_handle = pPhysicalDevices.get(i);
			VkPhysicalDevice physicalDevice = new VkPhysicalDevice(physical_device_handle, instance);
			physicalDevices[i] = physicalDevice;
		}
		
		// For every physical device, count how many queue families exist.
		for (int i = 0; i < physicalDevices.length; i++) {
			IntBuffer pQueueFamilyPropertyCount = memAllocInt(1); 			
			vkGetPhysicalDeviceQueueFamilyProperties(physicalDevices[i], pQueueFamilyPropertyCount, null);
			for (int j = 0; j < pQueueFamilyPropertyCount.get(0); j++) {
				VkQueueFamilyProperties.Buffer pQueueFamilyProperties = memBuff;
				vkGetPhysicalDeviceQueueFamilyProperties(physicalDevices[i], pQueueFamilyPropertyCount, null);
			}
		}
		
		
		VkDeviceCreateInfo[] pCreateInfos = new VkDeviceCreateInfo[physicalDevices.length];
		for (int i = 0; i < pCreateInfos.length; i++) {
			VkDeviceCreateInfo pCreateInfo = VkDeviceCreateInfo.create()
					.sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
					.
		}
		vkCreateDevice(physicalDevice, pCreateInfos, null, device);
	}
	
	private void _DeInitDevice() {
		
	}
	
	public void run() {
		System.out.println("It's running!");
		shutDown();
	}
	
	public static void main(String[] args) {
		Renderer r = new Renderer();
		r.run();
	}

}
