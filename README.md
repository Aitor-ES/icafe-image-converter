# icafe-image-converter
Simple Java application to convert images from one format to another (PNG, BMP, JPG, TIFF) using ICAFE library here: https://github.com/dragon66/icafe

In its version, it converts only BMP files to PNG, but it's easily adaptable to other formats.

If you need to convert many images, you'll find it useful, feel free to use it.

HOW TO USE IT:
You need to set an input folder and an output folder. The app will scan recursively the input folder and will convert every image it finds to the format specified. There's an optional if statement to convert only images of a certain format, not all images. Converted images will be written to the output folder.

BMP TO PNG RESULTS:
In my case, I used the app to convert ~600 BMP images to PNG to save space, and it reduced their size by 6.5 times.
