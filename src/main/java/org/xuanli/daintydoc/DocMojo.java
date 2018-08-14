package org.xuanli.daintydoc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * API Document for HTML
 * @apidoc-name 测试模块
 */
@Mojo(name="generate", defaultPhase = LifecyclePhase.PACKAGE)
public class DocMojo extends AbstractMojo {

    private static RootDoc root;

    @Parameter(property = "encoding", defaultValue = "${file.encoding}")
    private String encoding;
    @Parameter(property = "classpath", defaultValue = "${project.build.outputDirectory}")
    private String classpath;
    @Parameter(property = "source", defaultValue = "${project.build.sourceDirectory}")
    private String source;
    @Parameter(property = "output", defaultValue = "${project.build.directory}/doc-gen/")
    private String outdir;

    /**
     * @apidoc-url http://example.com/pur/add
     * @apidoc-name 采购单录入
     * @apidoc-param no|Int|true|合同号
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(String.format("project encoding: %s",encoding));
        getLog().info(String.format("project classpath: %s", classpath));
        getLog().info("plugin start run....");
        List<String> args = new ArrayList<String>();
        args.add("-doclet");
        args.add(Doclet.class.getName());
        args.add("-encoding");
        args.add(encoding);
        args.add("-classpath");
        args.add(classpath);
        args.addAll(FileUtils.scanSourceCode(new File(source)));
        com.sun.tools.javadoc.Main.execute(args.toArray(new String[args.size()]));
        List<DocPage> pages = new APIParser(root.classes()).start();
        for (DocPage page : pages) {
            try {
                new DocUtils(outdir).out(page);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }


    public static class Doclet {
        public Doclet() {}

        public static boolean start(RootDoc root) {
            DocMojo.root = root;
            return true;
        }
    }

    public static RootDoc getRoot() {
        return root;
    }
}
