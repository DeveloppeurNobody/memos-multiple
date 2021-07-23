package nio2.chap02_attrs

import java.io.IOException
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.*

object AttrsApp08ACL {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var aclList: MutableList<AclEntry>? = null;
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "BNP.txt");

        // read ACL using Files.getFileAttributeView
        var aclView = Files.getFileAttributeView(path, AclFileAttributeView::class.java);

        try {
            if(aclView != null) { aclList = aclView.acl };
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // read ACL using Files.getAttribute
        try {
            aclList = Files.getAttribute(path, "acl:acl", LinkOption.NOFOLLOW_LINKS) as MutableList<AclEntry>;
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // see the ACL entries
        aclList?.forEach {
            with(it) {
                println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                println("Principal: ${principal().name}");
                println("Type: ${type()}");
                println("Permissions: ${permissions()}");
                println("Flags: ${flags()}");
            }
        }

        // grant a new access
        try {
            // Lookup for the principal
            var user: UserPrincipal = path.fileSystem
                .userPrincipalLookupService
                .lookupPrincipalByName("ryu");

            // Get the ACL view
            var view: AclFileAttributeView = Files.getFileAttributeView(path, AclFileAttributeView::class.java);

            // Create a new entry
            var entry: AclEntry = AclEntry.newBuilder()
                .setType(AclEntryType.ALLOW)
                .setPrincipal(user)
                .setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.APPEND_DATA)
                .build();

            // read ACL
            var acl: MutableList<AclEntry> = view.acl;

            // Insert the new entry
            acl.add(0, entry);

            // re-write ACL
            view.acl = acl;
            // or, like this
            // Files.setAttribute(path, "acl:acl", acl, NOFOLLOW_LINKS);

        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}