package me.miquiis.onlyblock.common.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.forge.ForgeAdapter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.io.file.FilenameException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WorldEditUtils {

    public static void pasteSchematic(String fileName, World world, double X, double Y, double Z)
    {
        WorldEdit worldEdit = WorldEdit.getInstance();
        LocalConfiguration config = worldEdit.getConfiguration();
        File dir = worldEdit.getWorkingDirectoryPath(config.saveDir).toFile();

        try {
            File f = worldEdit.getSafeOpenFile(null, dir, fileName, "schematic", ClipboardFormats.getFileExtensionArray());
            ClipboardFormat format = ClipboardFormats.findByFile(f);
            ClipboardReader reader = format.getReader(new FileInputStream(f));
            Clipboard clipboard = reader.read();

            com.sk89q.worldedit.world.World adaptedWorld = ForgeAdapter.adapt(world);

            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);

            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(BlockVector3.at(X, Y, Z)).ignoreAirBlocks(true).build();

            try {
                Operations.complete(operation);
                editSession.flushSession();
            } catch (WorldEditException e) {
                e.printStackTrace();
            }

        } catch (FilenameException | IOException e) {
            e.printStackTrace();
        }
    }

}
