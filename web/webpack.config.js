const webpack = require('webpack');
const fs = require('fs');
const path = require("path");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

const LibName = "BijcPlotlyComponents";

function copyToResources() {
    const resourceFolder = path.resolve(__dirname, '..', 'gateway/src/main/resources/mounted/');
    const jsToCopy = path.resolve(__dirname, "dist/", `${LibName}.js`);
    const cssToCopy = path.resolve(__dirname, "dist/", `${LibName}.css`);
    const jsResourcePath = path.resolve(resourceFolder, `${LibName}.js`);
    const cssResourcePath = path.resolve(resourceFolder, `${LibName}.css`);

    const toCopy = [{ from: jsToCopy, to: jsResourcePath }, { from: cssToCopy, to: cssResourcePath }];

    // if the desired folder doesn't exist, create it
    if (!fs.existsSync(resourceFolder)) {
        fs.mkdirSync(resourceFolder)
    }

    toCopy.forEach(file => {
        console.log(`copying ${file} into ${resourceFolder}...`);

        try {
            fs.copyFile(file.from,file.to, (err) => {
                if (err) {
                    console.log(`Error when attempting to copy ${file.from} into ${file.to}`)
                }
            })
        } catch (err) {
            console.error(err);
            // rethrow to fail build
            throw err;
        }
    });
}

const config = {
    entry: {
        BijcPlotlyComponents: path.join(__dirname, "./typescript/bijc-client-chartcomponents.ts"),
    },

    output: {
        library: [LibName],  // name as it will be accessible by on the webpack when linked as a script
        path: path.join(__dirname, "dist"),
        filename: `${LibName}.js`,
        libraryTarget: "umd",
        umdNamedDefine: true
    },

    mode: "development",

    // Enable sourcemaps for debugging webpack's output.  Should be changed for production builds.
    devtool: "source-map",

    resolve: {
        extensions: [".jsx", ".js", ".ts", ".tsx", ".d.ts", ".css", ".scss"],
        modules: [
            path.resolve(__dirname, "node_modules")  // look at the local as well as shared node modules when resolving dependencies
        ]
    },

    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: {
                    loader: 'ts-loader',
                    options: {
                        transpileOnly: false,
                        experimentalWatchApi: true
                    }
                },
                exclude: /node_modules/
            },
            {
                test: /\.css$|.scss$/,
                use: [
                    {
                        loader: MiniCssExtractPlugin.loader
                    },
                    {
                        loader: 'css-loader'
                    },
                    {
                        loader: 'sass-loader'
                    },
                ]
            }
        ]
    },
    plugins: [
        {
            apply: (compiler) => {
                compiler.hooks.afterEmit.tap('AfterEmitPlugin', (stats) => {
                    console.log('After Emit');
                    copyToResources();
                });
            }
        },
        // pulls CSS out into a single file instead of dynamically inlining it
        new MiniCssExtractPlugin({
            filename: "[name].css"
        })
    ],
    externals: {
        "react": "React",
        "react-dom": "ReactDOM",
        "mobx": "mobx",
        "mobx-react": "mobxReact",
        "@inductiveautomation/perspective-client": "PerspectiveClient"
    },
    /*optimization: {
        splitChunks: {
            cacheGroups: {
                styles: {
                    name: 'styles',
                    test: /\.css$/,
                    chunks: 'all',
                    enforce: true,
                },
            },
        },
    },*/
};

module.exports = () => config;